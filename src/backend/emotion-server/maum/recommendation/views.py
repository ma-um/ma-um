from django.shortcuts import get_list_or_404
from rest_framework.decorators import api_view
import numpy as np
import pandas as pd
from .models import Music
from django.http import JsonResponse
import requests
from rest_framework.response import Response
import json
import logging

@api_view(['POST'])
def music_recommendation(request):

    # POST 로 emotions 를 받아오고 json 으로 불러오는 과정
    logger = logging.getLogger('test')
    logger.error(request)
    logger.error(request.body.decode('utf-8'))
    logger.error(request.data)
    emotion = request.data['emotions']
    # json_acceptable_string = response.replace("'", "\"") 
    # emotion = json.loads(json_acceptable_string)
    emotion = [emotion['fear'], emotion['surprise'], emotion['anger'], emotion['sadness'], emotion['neutrality'], emotion['happiness'], emotion['disgust'], emotion['pleasure'], emotion['embarrassment'], emotion['unrest'], emotion['bruise']]

    # 음악에 대한 감정을 스케일링
    musics = get_list_or_404(Music)
    tmp = []
    idx1 = 0
    for music in musics:
        tmp.append(list(map(float, [music.fear, music.surprise, music.anger, music.sadness, music.neutrality, music.happiness, music.disgust, music.pleasure, music.embarrassment, music.unrest, music.bruise])))
        for idx in range(len(tmp[idx1])):
            tmp[idx1][idx] = int((float(tmp[idx1][idx])+5)/13 * 100)
        idx1 += 1

    # 추천 알고리즘
    
    # arr 은 음악 감정 데이터
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

    # 추천 음악의 primary key를 value 로 넣어서 보낸다.
    data = {
		"musicIdList" :[
            {"id": int(musics[result[0]].pk)},
            {"id": int(musics[result[1]].pk)},
            {"id": int(musics[result[2]].pk)},
            {"id": int(musics[result[3]].pk)},
            {"id": int(musics[result[4]].pk)},
            {"id": int(musics[result[5]].pk)}
            ]
        }

    return JsonResponse({
		"musicIdList" :[
            {"id": int(musics[result[0]].pk)},
            {"id": int(musics[result[1]].pk)},
            {"id": int(musics[result[2]].pk)},
            {"id": int(musics[result[3]].pk)},
            {"id": int(musics[result[4]].pk)},
            {"id": int(musics[result[5]].pk)}
            ]
        }, status=200)