from django.shortcuts import get_object_or_404
from recommendation.models import Diary
from rest_framework.decorators import api_view
from django.views.decorators.http import require_GET, require_POST, require_http_methods
from django.http import JsonResponse
import torch
import gluonnlp as nlp
from .apps import EmotionConfig, BERTClassifier, BERTDataset
import requests
@api_view(['GET', 'POST'])
def diary2emotion(request, diary_id):

    # 실제로 실행할 때에는 주석처리 할 것
    diary = get_object_or_404(Diary, pk = diary_id)
    input_data = diary.content


    # # 실제로 실행할 때 주석을 풀 것
    # url = 'http://127.0.0.1:8000/diary2emotion'
    # response = requests.post(url)
    # input_data = response.body.content
    
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

    for idx in range(len(logits)):
        logits[idx] = int((float(logits[idx])+5)/13 * 100)
    
    data = {
        'text' : input_data,
        'result': f'{logits}'
    }
    
    return JsonResponse({'status': '임의로 200', 'data': data}, json_dumps_params={'ensure_ascii': False}, status=200) 
    # return JsonResponse({'status': response.status_code ,'data': data}, safe=True, json_dumps_params={'ensure_ascii': False}, status=200) 