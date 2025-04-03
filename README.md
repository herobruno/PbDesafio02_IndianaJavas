# Desafio 2 - PB Compass

O desafio implementa 3 microsserviços e um gateway.

## Microsserviços

- [ms-authorization/](./ms-authorization/)
- [ms-notification/](./ms-notification/)
- [ms-products/](./ms-products/)
- [gateway/](./gateway/)

Os 3 microsserviços estão funcionais individualmente, porém não conseguimos deixar o gateway e o docker funcionando corretamente.

Dessa forma, é possível executar cada microsserviço individualmente, e suas rotas funcionam, porém não existe 'integração' entre eles (com exceção do ms-authorization e o ms-notification, que se comunicam via RabbitMQ).

## Execução

O passo a passo para execução de cada microsserviço está indicado no README de cada um.

Antes de rodar os microsserviços, criar as bases de dados. Arquivo de inicialização: [init-db.sql](init-db.sql).

---

## 🔗 Repositório Original  
Este projeto é um fork do repositório original:  
[Gabriel-RQ/PbDesafio02_IndianaJavas](https://github.com/Gabriel-RQ/PbDesafio02_IndianaJavas)

---

## 👥 Contribuidores  

Agradecimento especial aos contribuidores deste projeto:

| Nome | GitHub |
|------|--------|
| Bruno | [@herobruno](https://github.com/herobruno) |
| Douglas Santos | [@DouglaSantos777](https://github.com/DouglaSantos777) |
| Petterini | [@petterini](https://github.com/petterini) |
| Gabriel RQ | [@Gabriel-RQ](https://github.com/Gabriel-RQ) |

Se você também contribuiu para este projeto, sinta-se à vontade para adicionar seu nome! 🚀
