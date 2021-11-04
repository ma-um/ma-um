from django.apps import AppConfig
from kobert.utils import get_tokenizer
from kobert.pytorch_kobert import get_pytorch_kobert_model

class EmotionConfig(AppConfig):
    default_auto_field = 'django.db.models.BigAutoField'
    name = 'emotion'
    bertmodel, vocab = get_pytorch_kobert_model()
    tokenizer = get_tokenizer()