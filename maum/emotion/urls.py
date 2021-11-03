from django.urls import path
from . import views

app_name = 'emotion'

urlpatterns = [
    path('text2emotion/', views.text2emotion, name='text2emotion'),
]
