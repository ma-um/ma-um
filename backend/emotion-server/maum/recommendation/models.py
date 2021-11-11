from django.db import models
from django.conf import settings

import torch
from torch import nn
import torch.nn.functional as F
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import gluonnlp as nlp
import numpy as np


# Create your models here.

class Text(models.Model):
    text = models.TextField()
    result = models.TextField()

class Song(models.Model):
    name = models.CharField(max_length=20)
    singer = models.CharField(max_length=40)
    jacket_url = models.TextField()
    fear = models.FloatField()
    surprise = models.FloatField()
    anger = models.FloatField()
    sadness = models.FloatField()
    neutrality = models.FloatField()
    happiness = models.FloatField()
    disgust = models.FloatField()
    pleasure = models.FloatField()
    embarrassment = models.FloatField()
    unrest = models.FloatField()
    bruise = models.FloatField()
    class Meta:
        managed = False