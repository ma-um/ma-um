from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import TextSerializer
from django.views.decorators.http import require_GET, require_POST, require_http_methods

import torch
import gluonnlp as nlp

from .apps import EmotionConfig, BERTClassifier, BERTDataset

# Create your views here.

@api_view(['GET', 'POST'])
def text2emotion(request):
    text = {
        'text' : '나리는 꽃가루에 눈이 따끔해 (아야) 눈물이 고여도 꾹 참을래 내 마음 한켠 비밀스런 오르골에 넣어두고서 영원히 되감을 순간이니까 우리 둘의 마지막 페이지를 잘 부탁해 어느 작별이 이보다 완벽할까? Love me only til this spring 오, 라일락, 꽃이 지는 날 goodbye 이런 결말이 어울려, 안녕 꽃잎 같은 안녕 하이얀 우리 봄날의 climax 아, 얼마나 기쁜 일이야 우우우우, 우우우우 Love me only til this spring 봄바람처럼 우우우우, 우우우우 Love me only til this spring 봄바람처럼 기분이 달아 콧노래 부르네 (랄라) 입꼬리는 살짝 올린 채 어쩜 이렇게 하늘은 더 바람은 또 완벽한 건지 오늘따라 내 모습 맘에 들어 처음 만난 그날처럼 예쁘다고 말해줄래 어느 이별이 이토록 달콤할까? Love resembles misty dream 오, 라일락, 꽃이 지는 날 goodbye 이런 결말이 어울려, 안녕 꽃잎 같은 안녕 하이얀 우리 봄날의 climax 아, 얼마나 기쁜 일이야 우우우우, 우우우우 Love resembles misty dream 뜬구름처럼 우우우우, 우우우우 Love resembles misty dream 뜬구름처럼 너도 언젠가 날 잊게 될까? 지금 표정과 오늘의 향기도? 단잠 사이에 스쳐간 봄날의 꿈처럼 오, 라일락, 꽃이 지는 날 goodbye 너의 대답이 날 울려, 안녕 약속 같은 안녕 하이얀 우리 봄날에 climax 아, 얼마나 기쁜 일이야 우우우우, 우우우우 Love me only til this spring 봄바람처럼 우우우우, 우우우우 Love me only til this spring 봄바람처럼 우우우우, 우우우우 Love resembles misty dream 뜬구름처럼 (뜬구름처럼) 우우우우, 우우우우 Love resembles misty dream 뜬구름처럼',
        'result': ''
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