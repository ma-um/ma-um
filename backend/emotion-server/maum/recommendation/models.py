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