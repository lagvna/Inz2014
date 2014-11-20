from django.template import RequestContext
from django.shortcuts import render_to_response
from django.http import HttpResponse
import django.utils.simplejson as json
from allauth.socialaccount import providers
from allauth.socialaccount.models import SocialLogin, SocialToken, SocialApp
from allauth.socialaccount.providers.facebook.views import fb_complete_login
from allauth.socialaccount.helpers import complete_social_login
from django.views.decorators.csrf import csrf_exempt
from pdapp.models import *

def index(request):
    return render_to_response("pdapp/index.html", RequestContext(request))


@csrf_exempt
def android_checkuser(request):
    print(request.COOKIES)
    response_data = {}
    if(request.user.is_authenticated()):
        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        response_data['result'] = 'success'
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')

@csrf_exempt
def android_updateevent(request):
    print(request.COOKIES)
    response_data = {}
    if(request.user.is_authenticated()):
        print('okej')
        user = request.user
        print(user.id)
        print(request.session.session_key)
        response_data['result'] = 'success'
        return HttpResponse(json.dumps(response_data), content_type='application/json')
    else:
        print('zle')
        response_data['result'] = 'failure'
        return HttpResponse(json.dumps(response_data), content_type='application/json')

@csrf_exempt
def android_login(request):
    access_token = request.GET['access_token']
    app = SocialApp.objects.get(provider='facebook')
    response_data = {}
    token = SocialToken(app = app, token = access_token)

    login = fb_complete_login(request, app, token)
    login.token = token

    login.state = SocialLogin.state_from_request(request)

    ret = complete_social_login(request, login)

    user = SocialToken.objects.get(token = token)
    print(user.account_id)

    response_data['result'] = 'success'
    response_data['message'] = ''

    return HttpResponse(json.dumps(response_data), content_type='application/json')

