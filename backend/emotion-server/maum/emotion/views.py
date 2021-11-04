from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import TextSerializer
from django.views.decorators.http import require_GET, require_POST, require_http_methods

import torch
import gluonnlp as nlp

from .classes import BERTClassifier, BERTDataset
from .apps import EmotionConfig

# Create your views here.

@api_view(['GET', 'POST'])
def text2emotion(request):
    text = {
        'text' : '분노',
        'result': 'asfdasfads'
    }
    input_data = text['text']
    device = torch.device("cuda:0" if (torch.cuda.is_available()) else "cpu")
    bertmodel, vocab = EmotionConfig.bertmodel, EmotionConfig.vocab
    max_len = 64
    batch_size = 64

    tokenizer = EmotionConfig.tokenizer
    tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)
    model = BERTClassifier(bertmodel, dr_rate=0.5).to(device)
    model.load_state_dict(torch.load('./학습 데이터 모델1.pt', map_location=device))

    def predict(predict_sentence):
        global logits

        data = [predict_sentence, '0']
        dataset_another = [data]
        another_test = BERTDataset(dataset_another, 0, 1, tok, max_len, True, False)
        test_dataloader = torch.utils.data.DataLoader(another_test, batch_size=batch_size, num_workers=0)
        model.eval()

        for batch_id, (token_ids, valid_length, segment_ids, label) in enumerate(test_dataloader):
            token_ids = token_ids.long().to(device)
            segment_ids = segment_ids.long().to(device)
            valid_length= valid_length
            label = label.long().to(device)
            out = model(token_ids, valid_length, segment_ids)

            test_eval=[]
            for i in out:
                logits=i
                logits = logits.detach().cpu().numpy()

    sentence = input_data
    predict(sentence)
    text['result'] = f'{logits}'
    
    serializer = TextSerializer(text)
    return Response(serializer.data)