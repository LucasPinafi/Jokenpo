import socket
import threading
from enum import Enum
from random import choice
from typing import *
from time import sleep


class Jogo(Enum):
    TESOURA = "TESOURA"
    PAPEL = "PAPEL"
    PEDRA = "PEDRA"
    LAGARTO = "LAGARTO"
    SPOCK = "SPOCK"

    @staticmethod
    def obter_vencedor(jogada: set) -> str:
        if jogada == {"TESOURA", "PAPEL"}:
            return "TESOURA"
        if jogada == {"PAPEL", "PEDRA"}:
            return "PAPEL"
        if jogada == {"PEDRA", "LAGARTO"}:
            return "PEDRA"
        if jogada == {"LAGARTO", "SPOCK"}:
            return "LAGARTO"
        if jogada == {"SPOCK", "TESOURA"}:
            return "SPOCK"
        if jogada == {"TESOURA", "LAGARTO"}:
            return "TESOURA"
        if jogada == {"LAGARTO", "PAPEL"}:
            return "LAGARTO"
        if jogada == {"PAPEL", "SPOCK"}:
            return "PAPEL"
        if jogada == {"SPOCK", "PEDRA"}:
            return "SPOCK"
        if jogada == {"PEDRA", "TESOURA"}:
            return "PEDRA"


SERVIDOR = socket.gethostbyname(socket.gethostname())
PORTA = 5051
print(SERVIDOR)
ADDR = SERVIDOR, PORTA
conexao = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
conexao.bind(ADDR)
HEADER = 32
FORMAT = 'utf-8'
JOGADAS: List[Jogo] = []
RECEBIDAS: List[str] = []
PLACAR = [0, 0]
QTD_RODADAS = 1
texto_imprimir = ""


def conectar_com_jogador() -> socket.socket:
    conexao.listen()
    conn, addr = conexao.accept()
    return conn 

def jogar(conn: socket.socket):
    while True:
        if QTD_RODADAS > 15:
            break
        jogada = escolher_jogada()
        conn.sendall((jogada.value+"\n").encode(FORMAT))
        JOGADAS.append(jogada)
        analisar(conn)

def receber_jogada(conn: socket.socket):
    while True:
        if QTD_RODADAS > 15:
            break
        jogada_recebida = conn.recv(HEADER)[2:].decode(FORMAT)
        RECEBIDAS.append(jogada_recebida)

def escolher_jogada() -> Jogo:
    possiveis_jogos = [Jogo.TESOURA, Jogo.PAPEL, Jogo.PEDRA, Jogo.LAGARTO, Jogo.SPOCK]
    escolha = choice(possiveis_jogos)
    return escolha

def analisar(conn: socket.socket):
    global PLACAR, texto_imprimir, QTD_RODADAS
    while len(JOGADAS) != len(RECEBIDAS):
        continue
    
    jogou = JOGADAS[-1].value
    recebido = RECEBIDAS[-1]
    rodada = {jogou, recebido}
    vitoria = Jogo.obter_vencedor(rodada)

    print(f'RODADA {QTD_RODADAS}')
    print(f'PYTHON escolheu {JOGADAS[-1].value}')
    print(f'JAVA escolheu {RECEBIDAS[-1]}')
    
    texto_imprimir += f'PYTHON escolheu {JOGADAS[-1].value}\nJAVA escolheu {RECEBIDAS[-1]}\n'

    if jogou == recebido:
        resultado = "Empate!"

    elif vitoria == jogou:
        resultado = "PYTHON ganhou a rodada!"
        PLACAR[0] += 1
    else:
        resultado = "PYTHON perdeu a rodada!"
        PLACAR[1] += 1

    texto_imprimir += f'{resultado}\nPLACAR: PYTHON {PLACAR[0]} X  JAVA {PLACAR[1]}\n\n**************NOVA RODADA**************'
    
    print(resultado)
    print(f'PLACAR: PYTHON {PLACAR[0]} X  JAVA {PLACAR[1]}')
    QTD_RODADAS += 1
    
    if QTD_RODADAS > 15:
         encerrar_conexao(conn)
         imprimir_resultado()
    
    sleep(5)
    print('\n**************NOVA RODADA**************')

def encerrar_conexao(conn: socket.socket) -> None:
    conn.close()

def imprimir_resultado():
    global texto_imprimir
    if PLACAR[0] == PLACAR[1]:
        texto_imprimir += "\n\nRESULTADO FINAL\n\n===> EMPATE"
    elif PLACAR[0] > PLACAR[1]:
        texto_imprimir += "\n\nRESULTADO FINAL\n\n===> PYTHON GANHOU"
    else:
        texto_imprimir += "\n\nRESULTADO FINAL\n\n===> JAVA GANHOU"
    with open("log_jogo.txt", "w") as arq:
        arq.write(texto_imprimir)
    
    
if __name__ == "__main__":
    conn = conectar_com_jogador()
    jogar_threading = threading.Thread(target=jogar, args=(conn, ))
    receber_threading = threading.Thread(target=receber_jogada, args=(conn, ))
    jogar_threading.start()
    receber_threading.start()

