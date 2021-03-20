стартуем миникуб:	
```shell script
minikube start --cpus=6 --memory=6g --vm-driver=hyperkit
```

просмотр нод:
```shell script
kubectl get nodes
```

просмотр нейспйсов:
```shell script 
kubectl get namespaces
```

создание нейспейса:
```shell script
kubectl create namespace monitoring
```

установка нейспейса по умолчанию:
```shell script
kubectl config set-context --current --namespace=monitoring
```

удалить ns:		
```shell script
kubectl delete namespace monitoring
```
-------------------------------------------------------------------------------------------


просмотр под во всех состояниях:	
```shell script
kubectl get pods -A
```


получаем доступы к демону докера внутри миникуба: 
```shell script
eval $(minikube docker-env)
```

```shell script
docker ps
```

-------------------------------------------------------------------------------------------

следить за изменениями в namespace в реальном времени:
```shell script
brew install watch
```
```shell script
kubectl get pod -w
```

-------------------------------------------------------------------------------------------

Пишем спецификацию пода
Просим кубик создать ресурсы из файла: 
```shell script
kubectl apply -f pod.yaml
```

основные параметры созданного пода: 
```shell script
kubectl describe pod hello-demo
```

заходим на виртуалку:	
```shell script
minikube ssh
```

смотрим, что на этом ip и порту отвечает наш сервис: 
```shell script
wget -qO- http://172.17.0.3:80/
```

выходим из виртуалки:	
```shell script
logout
```

-------------------------------------------------------------------------------------------
применим конфигурацию rs:	
```shell script
kubectl apply -f rs.yaml
```

удаляем ресурс rs:
```shell script
kubectl delete rs hello-rs-demo
```

```shell script
kubectl get all
```

-------------------------------------------------------------------------------------------
```shell script
kubectl apply -f deployment.yaml
```

история по deployment:
```shell script
kubectl rollout history deployment/hello-deployment deployment.apps/hello-deployment
```

откатить deployment:
```shell script
kubectl rollout undo deployment hello-deployment deployment.apps/hello-deployment
```

В случае необходимости можно заскейлить деплоймент:	
```shell script
kubectl scale deployment hello-deployment --replicas=4
```
-------------------------------------------------------------------------------------------
удалить всё: 
```shell script
kubectl delete all --all
```

```shell script
kubectl delete pod,svc,deploy --all
```
-------------------------------------------------------------------------------------------
находим среди сервисов имя нужного:	
```shell script
kubectl get service
```
получить адрес сервиса по имени сервиса и неймспесу в котором он находится: 
```shell script
minikube service monitoring-app-chart --url -n monitoring
```

Если сделаем курл на адрес, получим ответ: 
```shell script
curl http://192.168.64.2:32436/health
```
-------------------------------------------------------------------------------------------
за порт-форвардить сервис на локальную машину. Запросы с локальной машины на порт 10000 будут отправлять в указанный сервис на порт 9000
```shell script
kubectl port-forward svc/monitoring-app-chart 10000:9000
```

ЛОГИ:
```shell script
kubectl get events --sort-by='{.lastTimestamp}'
```

curl на сервис:
```shell script
curl $(minikube service my-app --url)
```

-------------------------------------------------------------------------------------------
HELM

создать:	
```shell script
helm create my-chart
```
открыть:	
```shell script
cd my-chart
```

Перед установкой по-настоящему посмотрим, как применились наши переменные:
```shell script
helm install myapp ./my-chart --dry-run
```

Устанавливаем зависимости:
```shell script
helm dependency update ./my-chart
```

установить релиз myapp и нашего чарта: 
```shell script
helm install myapp ./my-chart
```


