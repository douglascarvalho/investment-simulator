Investment Simulator
=================

## Arquitetura (overview)

- MVVM
- Android Architecture Components (LiveData, ViewModel)
- Repository Pattern
- Feature Module
- Koin (injeção de dependência)

![Image of Yaktocat](https://github.com/douglascarvalho/investment-simulator/blob/master/screens/input.png)
![Image of Yaktocat](https://github.com/douglascarvalho/investment-simulator/blob/master/screens/result.png)

## Principais Bibliotecas

| Plugin | Finalidade |
| ------ | ------ |
| coroutines | Trabalhar com operações assíncronas  |
| retrofit | Comunicação com a Api |
| koin & koin-viewmodel| Injeção de dependência |
| kotter-knife | View binding para Kotlin |
| mockito-kotlin | Mockito com sintaxe sugar para Kotlin  |
| mockwebserver | Servidor de mock para testes instrumentados |

## Módulos

### Feature

- **simulation** (contém os recursos necessários para apresentar a funcionalidade de simulação de investmento)

### Libraries

- **ui** (este módulo contém estilos e componentes visuais para o app (Design System))
- **core** (este módulo contém as classes bases e os imports necessários para os módulos de feature)
- **network** (este módulo contém a interface de comunicação com o backend)

## Por que módulos?

- Melhorar tempo de **build**
- Possibilitar **interoperabilidade** de feature / biblioteca **entre projetos**
- Possibilitar **InstantApps**
- Possibilitar **Dynamic Features**

## O que pode ser aperfeiçoado? (Não deu tempo de ajustar)

#### Manter os números das versões das libs em um único lugar
#### Melhorar os componentes visuais e o design como um todo

## Como rodar a cobertura de testes
Rode o seguinte comando no diretório **investment-simulator/**
```sh
$ ./gradlew clean jacocoTestReport
```

Atualmente a cobertura da feature simulation está em 98% (removendo as classes pojos):

![Image of Yaktocat](https://github.com/douglascarvalho/investment-simulator/blob/master/screens/simulation_coverage.png)

## Como instalar no device

Rode o seguinte comando no diretório **investment-simulator**
```sh
$ ./gradlew clean installDebug
```
