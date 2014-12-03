from django.conf.urls import patterns, include, url

from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'PerfectDay.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),
    url(r'^pdapp/', include('pdapp.urls')),
    url(r'^pdapp/accounts/', include('allauth.urls')),
    url(r'^admin/', include(admin.site.urls)),
)