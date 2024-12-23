# TCC - Alexandria

# O problema

De acordo com a Pesquisa Nacional de Saúde (PNS, 2020), 60,3% dos adultos no Brasil estão em caso de sobrepeso. Isso vêm gerado uma demanda por soluções que possam ajudar essas pessoas a manter uma rotina de alimentação saudável.

# Nossa Solução

Nosso objetivo é criar uma rede social onde os usuários podem interagir e compartilhar informações sobre alimentação, como receitas e posts variados. Nela, o usuário também poderá manter um registro diário do seu consumo de água e alimentos, gerando um relatório de tudo que foi consumido durante o dia, e comparando com valores sugeridos pela aplicação, ou definidos pelo próprio usuário. Ela contará com um sistema de pontuação e conquistas que devem influenciar o usuário a manter um streak de atividade na plataforma, que o recompensará por utilizar a rede de forma contínua.

# Funcionalidades

## Cadastro do usuário

  **O usuário deve informar:**

- Nome completo
- Email
- Senha
- Data de nascimento

## Autenticação do Usuário

Login com basic auth passando o email e a senha.

Opção de redefinir senha

Usuário poderá fazer login com uma conta do google ou do facebook.

## Após criar uma conta**:**

**O usuário deve informar:**

- Altura
- Peso
- Sexo
- Imagem de perfil

**O usuário deve escolher uma meta:** 

- Perder gordura
- Ganhar músculo
- Manter Peso

**O usuário deve informar quanta atividade física pratica na semana:**

- Pouca ou nenhuma
- Moderada (meia hora de exercício, quatro vezes por semana)
- Intensa (uma hora de exercício, pelo menos quatro vezes por semana)

## Com base nos dados do usuário:

Calcular o consumo de água diário recomendado.

Calcular o IMC do usuário.

Calcular a taxa metabólica basal do usuário.

Calcular uma ingestão calórica diária recomendada.

Calcular uma ingestão de macronutrientes diária recomendada.

## Página diária

**Cada dia terá um relatório:** 

O relatório deve conter: 

- Calorias consumidas e sugeridas.
- Macronutrientes consumidos e sugeridos.
- Consumo de água sugerido.
- Água consumida.

**Cada dia será dividido em:**

- Café da manhã.
- Lanche da Manhã.
- Almoço.
- Lanche da Tarde.
- Jantar.
- Ceia.

## Adicionar alimentos em um período do dia:

O usuário poderá adicionar os alimentos consumidos durante o período. O alimento será buscado no banco, e o usuário deverá informar a quantidade consumida.

O usuário poderá alterar ou remover alimentos inseridos no período.

Em cada período será exibido o total das calorias e dos nutrientes consumidos.

 

## Adicionar água ingerida durante o dia

O usuário poderá inserir uma quantidade de água quantas vezes quiser durante o dia.

## Página do usuário:

**Para qualquer usuário:**

No topo: Informações básicas do usuário

Lista de posts do Usuário

Lista de receitas do Usuário

Lista de receitas e posts curtidos pelo usuário

Lista de conquistas desbloqueadas pelo usuário

Opção de follow/unfollow

**Para o usuário logado:**

Lista das informações privadas do usuário:

- IMC, taxa basal, altura
- Histório de pesos e peso atual
- Opção de registrar novo peso
- Opção de alterar informações
- Opção de escolha da tag exibida nas conquistas
- Ingestões diárias recomendadas e opção de mudar cada uma.

## Página de alimentos:

Buscar e exibir alimentos do banco de dados por nome

**Usuário pode adicionar um alimento personalizado:**

Cada alimento deve conter suas calorias, proteínas, carboidratos e lipidios informados.

Usuário ao pesquisar por alimentos pode filtrar entre alimentos da tabela oficial e alimentos da comunidade.

## Página de receitas:

**Usuário deve ser capaz de criar uma receita personalizada:**

Cada receita pode ter vários alimentos.

Cada receita pode ser pública ou privada

Cada receita deve exibir a soma dos valores nutricionais de cada alimento dela.

Cada receita deve possuir uma lista de comentários e sua quantidade de curtidas.

Usuário pode adicionar receitas diretamente nas suas refeições.

**Devem existir duas listas de receitas:** 

- Lista de receitas públicas mais curtidas
- Lista das receitas recentes dos usuários seguidos

**Deve ser possível copiar uma receita pública:**

Após a cópia, a receita poderá ser utilizada em sua própria dieta, mas será marcada como cópia no perfil.

**Deve ser possível filtrar a página de receitas por preferências e alergias**

**Deve ser possível:**

- Curtir uma receita
- Comentar em uma receita
- Acessar o perfil do autor da receita

**Cada receita terá uma lista de receitas com valor nutricional semelhante em uma área de sugestões.**

## Página de Posts:

**Deve existir um formulário para criação de um post:**

Cada post pode ser público ou privado

Cada post deve possuir uma lista de comentários e sua quantidade de curtidas.

**Lista dos posts recentes dos usuários seguidos**

**Deve ser possível:**

- Curtir um post
- Comentar em um post
- Acessar o perfil do autor do post

## Página de Ranking:

Deve listar o top 20 dos usuários com mais pontos.

Deve ser possível buscar algum usuário por nome.

## Chat entre usuários:

Cada usuário poderá mandar mensagens diretas para algum outro usuário.

Conquistas

Cada usuário terá uma página com 20 conquistas, que começam bloqueadas.

Cada conquista terá uma barra de progresso.

Cada conquista terá uma tag de recompensa.

Tags podem ser de bronze, prata ou ouro, dependendo da dificuldade

Conquistas podem ser: 

- Streaks
- Quantidade de posts
- Quantidade de curtidas
- Quantidade de receitas

## Ranking:

Cada usuário terá pontos, começando com zero.

Cada usuário terá uma posição no ranking geral.

Cada conquista obtida irá render pontos:

- Bronze - 250 pontos
- Prata - 500 pontos
- Ouro - 1000 pontos

Quando o usuário atingir o recomendado de água no dia ganhará 20 pontos. Caso ele esteja em um streak de dias consecutivos ganhará 40.

Adicionar comida no dia - 25 pontos (limite de 1x/dia). Streak - 50 pontos

Adicionar uma nova receita - 50 pontos. (limite de 1x/dia)

Realizar um novo post - 5 pontos

Comentar em um post - 10 pontos

## Compartilhamento em outras redes:

**Deve ser possível gerar uma imagem ao desbloquear uma conquista e compartilha-lá no twitter ou no facebook.**

**Deve ser possível compartilhar uma receita no twitter ou no facebook.**

# Tecnologias

- React
- Spring
- Websockets para o desenvolvimento do chat entre usuários.
- Biblioteca [react-share](https://www.npmjs.com/package/react-share) (compartilhar nas redes sociais)
- Biblioteca  [html-to-image](https://www.npmjs.com/package/html-to-image) (criar imagem para compartilhamento)
- APIs do Google e do Facebook para realização do login.
- Docker
- Postgres
- TACO (Tabela Brasileira de Composição de Alimentos) como fonte para nossa base de alimentos.