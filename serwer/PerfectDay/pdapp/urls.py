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
)
