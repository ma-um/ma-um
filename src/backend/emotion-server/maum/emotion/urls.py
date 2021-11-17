from django.urls import path
from . import views

app_name = 'emotion'

urlpatterns = [
    path('diary2emotion/', views.diary2emotion, name='diary2emotion'),
]
