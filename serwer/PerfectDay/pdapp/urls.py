from django.conf.urls import patterns, url

from pdapp import views

urlpatterns = patterns('',
    url(r'^$', views.index, name='index'),
    url(r'^login/', views.android_login, name = 'login'),
    url(r'^checkuser/', views.android_checkuser, name = 'checkuser'),
    url(r'^updateevent/', views.android_updateevent, name = 'updateevent'),
    url(r'^addevent/', views.android_addevent, name = 'addevent'),
    url(r'^getallevents/', views.android_getallevents, name = 'getallevents'),
    url(r'^addguest/', views.android_addguest, name = 'addguest'),
    url(r'^remove/', views.android_remove, name = 'remove'),
    url(r'^getallguests/', views.android_getallguests, name = 'getallguests'),
    url(r'^addcontact/', views.android_addcontact, name = 'addcontact'),
    url(r'^getallcontacts/', views.android_getallcontacts, name = 'getallcontacts'),
    url(r'^addnote/', views.android_addnote, name = 'addnote'),
    url(r'^getallnotes/', views.android_getallnotes, name = 'getallnotes'),
    url(r'^addgift/', views.android_addgift, name = 'addgift'),
    url(r'^getallgifts/', views.android_getallgifts, name = 'getallgifts'),
)
