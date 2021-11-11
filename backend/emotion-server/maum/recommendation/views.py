from django.shortcuts import render, redirect, get_object_or_404, get_list_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import TextSerializer
import numpy as np
import pandas as pd
from .models import Song
import requests

@api_view(['GET', 'POST'])
def emotion_recommendation(request):
    url = 'http://127.0.0.1:8000/emotion/text2emotion/'
    response = requests.get(url)
    # input
    # 일기로부터 가져온 감정 리스트
    # print(response)
    # params = {'text': 'diary', 'result': ''}
    # tmp = response.get(url, params=params)
    # print(tmp)
    emotion1 = response.json()['result']
    arr = list(emotion1.split(' '))
    arr[0] = arr[0][1::]
    emotion1 = []
    for i in arr:
        if i != '' and i[-3::] != '\n' and i != arr[-1]:
            emotion1.append(float(i))
    # print(result)
    # emotion =([1,2,3,4,5,6,7,8,9,10,11])
    # arr 은 음악 감정 데이터
    songs = get_list_or_404(Song)
    tmp = []
    for song in songs:
        tmp.append(list(map(float, [song.fear, song.surprise, song.anger, song.sadness, song.neutrality, song.happiness, song.disgust, song.pleasure, song.embarrassment, song.unrest, song.bruise])))
    arr = np.array(tmp)

    result= np.subtract(arr,emotion1)
    result = np.abs(result)
    sum = result.sum(axis=1)
    sum = sum,np.arange(len(sum))
    sum = np.transpose(sum)
    sumdf= pd.DataFrame(sum,columns=['rating','number'])
    sum = sumdf.sort_values(by=["rating"])
    num = sum.set_index('number')
    result = sum.number
    result = result.to_numpy()

    text = {
        'text' : f'{emotion1}',
        'result': f'{result}'
    }
    
    serializer = TextSerializer(text)
    return Response(serializer.data)