from django.urls import path
from . import views

app_name = 'recommendation'

urlpatterns = [
    path('emotion_recommendation/', views.emotion_recommendation, name='emotion_recommendation'),
]
