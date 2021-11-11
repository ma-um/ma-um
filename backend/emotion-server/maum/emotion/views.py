from django.shortcuts import get_object_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import TextSerializer
from django.views.decorators.http import require_GET, require_POST, require_http_methods

import torch
import gluonnlp as nlp

from .apps import EmotionConfig, BERTClassifier, BERTDataset

# Create your views here.

@api_view(['GET', 'POST'])
def diary2emotion(request):
    if request.method == 'GET':
        diary = {
            'diary' : "You know that I love you(sweety) Can't you see my eyes There's only one, the realslow 내가 달리는 길은 Love Love Love Love 허나 그 길엔 온통 덫 덫 덫 덫 피할 수 없는 함정은 맘의 겁 겁 겁 겁 마치 늪처럼 용기를 삼켜 점점 난 작아져 사라져 가는 얼굴의 밝은 표정 내 고백에 등돌린 채 외면할까봐 자꾸 두려워  바늘 같은 걱정을 베고서 오지 않는 잠을 청하고 꿈보다 더 생생한 네 생각 때문에 끝내 밤을 새워 Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah  너라는 곳을 향해 외로워도 가는 길 Love Love Love Love 몇 번을 넘어져도 일어서 갈 테지 But But But But 잠마저 못 들도록 너를 보다 걸려든 병 병 병 병 네 사랑 갖지 못하면 나을 수 없지 영영 영원토록 죽도록 너의 허락만 기다리고 몇 년이든 몇 생애든 너를 위해 존재하겠지만  바늘 같은 걱정을 베고서 오지 않는 잠을 청하고 꿈보다 더 생생한 네 생각 때문에 끝내 밤을 새워 Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ahFeels like insomnia ah ah   ah 불타는 이 사랑 그리움에 지쳐 내리는 비 같은 눈물에 젖어도 식지 않는걸  매일 입술을 물고서 오지 않는 잠을 청하고 꿈보다 더 생생한 네 생각 때문에 끝내 밤을 새워 Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah ah Feels like insomnia ah",
            'result': ''
        }
        input_data = diary['diary']
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
        diary['result'] = f'{logits}'
    serializer = TextSerializer(diary)
    return Response(serializer.data)