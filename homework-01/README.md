## Microservice Architecture
### Тема: Основы работы с Kubernetes
#### Задание №1:
- [x] Создать минимальный сервис, который 
1) отвечает на порту 8000
2) имеет http-метод 
GET /health/
RESPONSE: {"status": "OK"}

- [x] Cобрать локально образ приложения в докер. Запушить образ в dockerhub

- [x] Написать манифесты для деплоя в k8s для этого сервиса. Манифесты должны описывать сущности Deployment, Service, Ingress. 
- [ ] В Deployment могут быть указаны Liveness, Readiness пробы. 
- [x] Количество реплик должно быть не меньше 2. Image контейнера должен быть указан с Dockerhub.

- [x] Хост в ингрессе должен быть arch.homework. В итоге после применения манифестов GET запрос на http://arch.homework/health должен отдавать {“status”: “OK”}.

- [x] На выходе предоставить
0) ссылку на github c манифестами. Манифесты должны лежать в одной директории, так чтобы можно было их все применить одной командой kubectl apply -f .
1) url, по которому можно будет получить ответ от сервиса (либо тест в postmanе).

Задание со звездой* (+5 баллов):
- [x] В Ingress-е должно быть правило, которое форвардит все запросы с /otusapp/{student name}/* на сервис с rewrite-ом пути. Где {student name} - это имя студента

------------------------------------------------------------------
#### Примечания по решению:

Образ в dockerhub: 
    
    neversleeps/otus-arch-homework-01

сurl, по которому можно будет получить ответ от сервиса: 

    curl arch.homework/otusapp/iakonyakina/health
    
Пример сurl, если не прописан host в /etc/hosts:
    
    curl -H "Host: arch.homework" http://192.168.64.2/otusapp/iakonyakina/health
    
где значение адреса сервиса можно получить по имени сервиса и namespace, в котором он находится: 
    
    minikube service hello-service --url -n myapp
