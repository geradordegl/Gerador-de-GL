# Gerador de GL
Gerador de gramáticas locais a partir de exemplos
A maior parte do processamento do programa é chamado a partir da classe "Grafos Presenter".

a classe "UnitexFunctions" configura os paths e as opções de execução do unitextool e também fornece as funções necessárias para o preprocessamento das frases marcadas.
a classe "EventAdpter" monta os exemplos separando suas caracteristicas, literal, lema, classe gramátical, flexões e para isso utiliza-se várias regras de regex.
a classe "Generalizer" faz o cálculo da frequência das características e gera um map final com a solução generalizada.
a classe "Graph" monta o grafo conforme o padrão do grf.
