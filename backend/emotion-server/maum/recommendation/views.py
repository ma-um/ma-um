from rest_framework.response import Response
from rest_framework.decorators import api_view
from .serializers import TextSerializer
import numpy as np
import pandas as pd


@api_view(['GET', 'POST'])
def emotion_recommendation(request):
    arr = np.array([1,3,4,55,23,44,55,13,6,63,32])
    # 11부터 36
    an= np.arange(11,36)
    # input
    # 감정 추출 리스트
    emotion =([1,2,3,4,5,6,7,8,9,10,11])
    # arr 은 음악 감정 데이터
    arr = np.array([[1,3,4,55,23,44,55,13,6,63,32],[1,3,4,55,23,44,55,13,6,63,31],[1,3,4,55,23,44,55,13,6,63,30]])
    result= np.subtract(arr,emotion)
    result = np.abs(result)
    sum =result.sum(axis=1)
    sum = sum,np.arange(len(sum))
    sum =np.transpose(sum)
    sumdf= pd.DataFrame(sum,columns=['rating','number'])
    sum = sumdf.sort_values(by=["rating"])
    num=sum.set_index('number')
    result = sum.number
    result =result.to_numpy()
    result = np.transpose([result])
    # # 음악
    # [5, 1, 0, 0, 0, 0, 0, 0]
    # [1, 1, 1, 1, 1, 1, 1, 1]

    # # 일기
    # [4, 0, 0, 0, 0, 0, 0, 0]
    # [1, 1, 1, 1, 1, 1, 1, 1]
    text = {
        'text' : '분노',
        'result': ''
    }
    
    serializer = TextSerializer(text)
    return Response(serializer.data)