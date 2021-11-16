from django.shortcuts import render, redirect, get_object_or_404, get_list_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import DiarySerializer
import numpy as np
import pandas as pd
from .models import Music
import requests

@api_view(['GET', 'POST'])
def emotion_recommendation(request):
    url = 'http://127.0.0.1:8000/emotion/diary2emotion/'
    response = requests.get(url)
    # input 일기로부터 가져온 감정 리스트
    emotion = response.json()['result'][1:-2]
    emotion = list(map(int, emotion.split('. ')))
    # emotion = [80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    # arr 은 음악 감정 데이터
    musics = get_list_or_404(Music)
    tmp = []
    idx1 = 0
    for music in musics:
        tmp.append(list(map(float, [music.fear, music.surprise, music.anger, music.sadness, music.neutrality, music.happiness, music.disgust, music.pleasure, music.embarrassment, music.unrest, music.bruise])))
        for idx in range(len(tmp[idx1])):
            tmp[idx1][idx] = int((float(tmp[idx1][idx])+5)/13 * 100)
        idx1 += 1
    
    arr = np.array(tmp)
    result= np.subtract(arr, emotion)
    result = np.abs(result)
    sum = result.sum(axis=1)
    sum = sum,np.arange(len(sum))
    sum = np.transpose(sum)
    sumdf= pd.DataFrame(sum,columns=['rating','number'])
    sum = sumdf.sort_values(by=["rating"])
    num = sum.set_index('number')
    result = sum.number
    result = result.to_numpy()

    diary = {
        'text' : f'{emotion}',
        'music1': f'{musics[result[0]].name, musics[result[0]].singer, musics[result[0]].jacket_url}',
        'music2': f'{musics[result[1]].name, musics[result[1]].singer, musics[result[1]].jacket_url}',
        'music3': f'{musics[result[2]].name, musics[result[2]].singer, musics[result[2]].jacket_url}'
    }
    
    serializer = DiarySerializer(diary)
    return Response(serializer.data)