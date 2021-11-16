from django.db import models
from django.conf import settings
from recommendation.models import Diary, Music
import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np


# Create your models here.

class Text(models.Model):
    diary = models.TextField()
    result = models.TextField()

class BERTDataset(Dataset):
    def __init__(self, dataset, sent_idx, label_idx, bert_tokenizer, max_len,
                 pad, pair):
        transform = nlp.data.BERTSentenceTransform(
            bert_tokenizer, max_seq_length=max_len, pad=pad, pair=pair)

        self.sentences = [transform([i[sent_idx]]) for i in dataset]
        self.labels = [np.int32(i[label_idx]) for i in dataset]

    def __getitem__(self, i):
        return (self.sentences[i] + (self.labels[i], ))

    def __len__(self):
        return (len(self.labels))

class BERTClassifier(nn.Module):
    def __init__(self,
                 bert,
                 hidden_size = 768,
                 # 클래스 수 (감정 갯수) 조정
                 num_classes=11,
                 dr_rate=None,
                 params=None):
        super(BERTClassifier, self).__init__()
        self.bert = bert
        self.dr_rate = dr_rate    
        self.classifier = nn.Linear(hidden_size , num_classes)
        if dr_rate:
            self.dropout = nn.Dropout(p=dr_rate)
    
    def gen_attention_mask(self, token_ids, valid_length):
        attention_mask = torch.zeros_like(token_ids)
        for i, v in enumerate(valid_length):
            attention_mask[i][:v] = 1
        return attention_mask.float()

    def forward(self, token_ids, valid_length, segment_ids):
        attention_mask = self.gen_attention_mask(token_ids, valid_length)
        _, pooler = self.bert(input_ids = token_ids, token_type_ids = segment_ids.long(), attention_mask = attention_mask.float().to(token_ids.device))
        if self.dr_rate:
            out = self.dropout(pooler)
        return self.classifier(out)


class Emotion(models.Model):
    emotion_id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20)
    emoji = models.TextField()
    class Meta:
        db_table = u'emotion'

class DiaryEmotion(models.Model):
    diary_emotion_id = models.AutoField(primary_key=True)
    diary_id = models.ForeignKey(Diary, on_delete=models.CASCADE)
    emotion_id = models.ForeignKey(Emotion, on_delete=models.CASCADE)
    value = models.CharField(max_length=20)
    class Meta:
        db_table = u'diary_emotion'