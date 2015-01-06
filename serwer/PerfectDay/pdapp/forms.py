__author__ = 'lagvna'
from django import forms

class EventForm(forms.Form):
    name = forms.CharField(max_length = 200)
    place = forms.CharField(max_length=200)
    date = forms.CharField(max_length=200)
    note = forms.CharField(max_length=1000)