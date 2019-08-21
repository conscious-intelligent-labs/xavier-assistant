from django.http import HttpResponse


def index(request):
    return HttpResponse("Xavier Skills")

def status(request):
    return HttpResponse("The 'Skills' service is online.")

