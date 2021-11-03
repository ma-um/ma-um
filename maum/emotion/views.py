from django.shortcuts import render, redirect, get_object_or_404, get_list_or_404
from rest_framework import serializers
from rest_framework.serializers import Serializer
from .models import Text
from rest_framework.decorators import authentication_classes, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from .serializers import TextSerializer
from django.http import JsonResponse, HttpResponse
from django.views.decorators.http import require_GET, require_POST, require_http_methods
from collections import OrderedDict

import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np

from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

from .classes import BERTClassifier, BERTDataset

# Create your views here.

@api_view(['GET', 'POST'])
def text2emotion(request):
    text = {
        'text' : '멈춘 시간 속 잠든 너를 찾아가 아무 막아도 결국 너의 곁인 걸 길고 긴 여행을 끝내 이젠 돌아가 너라는 집으로 지금 다시 Way back home 아무리 힘껏 닫아도 다시 열린 서랍 같아 하늘로 높이 날린 넌 자꾸 내게 되돌아와 힘들게 삼킨 이별도 다 그대로인 걸 Oh oh oh 수없이 떠난 길 위에서 난 너를 발견하고 비우려 했던 맘은 또 이렇게 너로 차올라 발걸음의 끝에  니가 부딪혀 그만 그만 멈춘 시간 속 잠든 너를 찾아가 아무리 막아도 결국 너의 곁인 걸 길고 긴 여행을 끝내 이젠 돌아가 너라는 집으로 지금 다시 Way back home  조용히 잠든 방을 열어 기억을 꺼내 들어 부서진 시간 위에서 선명히 너는 떠올라 길 잃은 맘 속에 널 가둔 채 살아 그만 그만  멈춘 시간 속 잠든 너를 찾아가 아무리 막아도 결국 너의 곁인 걸 길고 긴 여행을 끝내 이젠 돌아가 너라는 집으로 지금 다시 Way back home  세상을 뒤집어 찾으려 해 오직 너로 완결된 이야기를 모든 걸 잃어도 난 너 하나면 돼  빛이 다 꺼진 여기 나를 안아줘  눈을 감으면 소리 없이 밀려와 이 마음 그 위로 넌 또 한 겹 쌓여가 내겐 그 누구도 아닌 니가 필요해 돌아와 내 곁에 그날까지 Im not done',
        'result': 'asfdasfads'
    }
    input_data = text['text']

    device = torch.device("cuda:0" if (torch.cuda.is_available()) else "cpu")
    bertmodel, vocab = get_pytorch_kobert_model()

    max_len = 64
    batch_size = 64
    warmup_ratio = 0.1
    num_epochs = 5
    max_grad_norm = 1
    log_interval = 200
    learning_rate =  5e-5

    tokenizer = get_tokenizer()
    tok = nlp.data.BERTSPTokenizer(tokenizer, vocab, lower=False)
    model = BERTClassifier(bertmodel, dr_rate=0.5).to(device)
    model.load_state_dict(torch.load('./학습 데이터 모델1.pt', map_location=device))
    # model = torch.load('./학습 데이터 모델2.pt', map_location=device)
    # model = torch.load_state_dict('./학습 데이터 모델1.pt', map_location=device)
    # model.load_state_dict(torch.load('./학습 데이터 모델1.pt'))
    model.eval()
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
    print(logits, type(logits))
    # logit = ' '.join(logits)
    text['result'] = f'{logits}'
    
    serializer = TextSerializer(text)
    return Response(serializer.data)