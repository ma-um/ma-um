from django.urls import path
from . import views

app_name = 'recommendation'

urlpatterns = [
    path('music_recommendation/', views.music_recommendation, name='music_recommendation'),
]
