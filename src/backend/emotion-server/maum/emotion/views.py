from rest_framework.decorators import api_view
from django.http import JsonResponse
import torch
import gluonnlp as nlp
from .apps import EmotionConfig, BERTClassifier, BERTDataset
import logging
@api_view(['POST'])
def diary2emotion(request):
    logger = logging.getLogger('test')
    logger.error(request)
    logger.error(request.body.decode('utf-8'))
    # logger.error(request.data)
    # input_data = request.data['content']
    input_data = request.body.decode('utf-8')
    # logger.error('13221323o')

    device = torch.device("cpu")
    bertmodel, vocab = EmotionConfig.bertmodel, EmotionConfig.vocab
    max_len = 64
    batch_size = 64
    tokenizer = EmotionConfig.tokenizer
    tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)
    model = BERTClassifier(bertmodel, dr_rate=0.5).to(device)
    model.load_state_dict(torch.load('./model.pt', map_location=device))

    # 감정 추출 함수
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

    # 감정 추출
    predict(input_data)

    # 감정 스케일링
    for idx in range(len(logits)):
        logits[idx] = int((float(logits[idx])+5)/13 * 100)
    
    data = {
        'content' : input_data,
        'result': f'{logits}'
    }
    
    # return JsonResponse({'data': data}, safe=True, json_dumps_params={'ensure_ascii': False}, status=200) 
    return JsonResponse({'content': input_data, "result": f'{logits}'}) 