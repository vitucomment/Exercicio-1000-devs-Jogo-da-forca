package com.mesttra.johnson.project.game.forca;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class mainJogoForca {

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner input = new Scanner(System.in);

		while (true) {
			ImprimeMenu();
			int escolha = pegaEscolha();
			System.out.println("");
			String palavra = geraPalavra(escolha);
			int dificuldade = dificuldade(escolha, palavra);

			char[] listaPalavra = geraListaDaPalavra(palavra);
			char[] palavraOcultada = ocultaPalavras(listaPalavra.clone());
			int contador = dificuldade;
			System.out.println("__________O JOGO VAI COMEÇAR__________\n");
			while (contador >= 0) {
				System.out.println("Tentativas restantes: " + contador);
				if (conferePalavra(palavra, listaPalavra, palavraOcultada) == true)
					;
				else
					contador--;
				if (confereVitoria(palavraOcultada) == true) {
					imprimeMensagemVencedor();
					break;
				}
				if (contador == 0) {
					imprimeMensagemPerdedor();
					break;
				}
			}
			System.out.print("========== DESEJA JOGAR NOVAMENTE? ==========[S / N]:  ");
			String jogarNovamente = input.nextLine();
			while (!jogarNovamente.equalsIgnoreCase("s") && !jogarNovamente.equalsIgnoreCase("n")) {
				System.out.println("========== DIGITE [S] PARA JOGAR NOVAMENTE OU [N] PARA ENCERRAR O JOGO ==========");
				jogarNovamente = input.nextLine();
			}
			if (jogarNovamente.equalsIgnoreCase("N")) {
				System.out.println("========== FIM DE JOGO ==========");
				break;
			}
		}
		input.close();
	}

	private static String geraPalavra(int escolha) throws FileNotFoundException {
		Scanner input = null;
		if (escolha == 1) {
			input = new Scanner(new FileReader("palavrasFaceis.txt"));
		} else if (escolha == 2) {
			input = new Scanner(new FileReader("palavrasMedias.txt"));
		} else if (escolha == 3) {
			input = new Scanner(new FileReader("palavrasDificeis.txt"));
		}
		ArrayList<String> palavras = new ArrayList<>();
		while (input.hasNextLine()) {
			String line = input.nextLine();
			palavras.add(line);
		}
		Random rdm = new Random();
		String palavra = palavras.get(rdm.nextInt(palavras.size()));
		return palavra;
	}

	private static Scanner input() {
		Scanner input = new Scanner(System.in);
		return input;
	}

	private static char pedeChute() {
		Scanner input = input();
		System.out.print("Digite uma letra: ");
		char chute = input.next().charAt(0);
		System.out.println("");
		while (Character.isDigit(chute)) {
			System.out.print("Você digitou um número! Digite apenas uma letra: ");
			chute = input.next().charAt(0);
		}
		return chute;
	}

	private static char[] geraListaDaPalavra(String palavraQueVaiSerConvertida) {
		String palavra = palavraQueVaiSerConvertida.toLowerCase();
		char[] listaLetras = new char[palavra.length()];
		for (int i = 0; i < palavra.length(); i++) {
			listaLetras[i] = palavra.charAt(i);
		}
		return listaLetras;
	}

	private static char[] ocultaPalavras(char[] listaPalavras) {
		for (int i = 0; i < listaPalavras.length; i++) {
			listaPalavras[i] = '•';
		}
		return listaPalavras;
	}

	private static boolean conferePalavra(String palavra, char[] listaPalavra, char[] palavraOcultada) {
		System.out.print("Palavra a ser descoberta: ");
		System.out.println(palavraOcultada);
		char letraDigitada = pedeChute();
		boolean retorno = false;
		for (int i = 0; i < palavra.length(); i++) {
			if (listaPalavra[i] == letraDigitada) {
				palavraOcultada[i] = letraDigitada;
				retorno = acertou(1);
			}
		}
		if (retorno == true)
			return true;
		else
			return acertou(0);
	}

	private static void escolhasDeDificuldade() {
		System.out.println("\t[1] --> Fácil");
		System.out.println("\t[2] --> Médio");
		System.out.println("\t[3] --> Difícil");
		System.out.print("Escolha: ");
	}

	private static void ImprimeMenu() {
		System.out.println("========== Bem vindo ao jogo da forca ==========\n");
		System.out.println("Escolha a dificuldade do jogo:");
		escolhasDeDificuldade();
	}

	private static int pegaEscolha() {
		Scanner input = input();
		try {
			int escolha = input.nextInt();
			while (escolha != 1 && escolha != 2 && escolha != 3) {
				System.out.println("Escolha entre as dificuldades: ");
				escolhasDeDificuldade();
				escolha = input.nextInt();
			}
			return escolha;
		} catch (InputMismatchException ex) {
			System.out.println("Digite um valor válido!");
			escolhasDeDificuldade();
			return pegaEscolha();
		}
	}

	private static int dificuldade(int escolha, String palavra) {
		double multiplicador = 0.6;
		switch (escolha) {
		case 1:
			multiplicador = 1.0;
			break;
		case 2:
			multiplicador = 0.9;
			break;
		case 3:
			multiplicador = 0.8;
			break;
		}
		return (int) (palavra.length() * multiplicador);
	}

	private static boolean acertou(int valor) {
		if (valor != 0)
			return true;
		else
			return false;
	}

	private static boolean confereVitoria(char[] palavraOcultada) {
		for (int i = 0; i < palavraOcultada.length; i++) {
			if (palavraOcultada[i] == '•')
				return false;
		}
		return true;
	}

	private static void imprimeMensagemVencedor() {
		System.out.println("Parabéns, você ganhou!");
		System.out.println("       ___________      ");
		System.out.println("      '._==_==_=_.'     ");
		System.out.println("      .-\\:      /-.    ");
		System.out.println("     | (|:.     |) |    ");
		System.out.println("      '-|:.     |-'     ");
		System.out.println("        \\::.    /      ");
		System.out.println("         '::. .'        ");
		System.out.println("           ) (          ");
		System.out.println("         _.' '._        ");
		System.out.println("        '-------'       ");
	}

	private static void imprimeMensagemPerdedor() {
		System.out.println("Puxa, você foi enforcado!");
		System.out.println("    _______________         ");
		System.out.println("   /               \\       ");
		System.out.println("  /                 \\      ");
		System.out.println("//                   \\/\\  ");
		System.out.println("\\|   XXXX     XXXX   | /   ");
		System.out.println(" |   XXXX     XXXX   |/     ");
		System.out.println(" |   XXX       XXX   |      ");
		System.out.println(" |                   |      ");
		System.out.println(" \\__      XXX      __/     ");
		System.out.println("   |\\     XXX     /|       ");
		System.out.println("   | |           | |        ");
		System.out.println("   | I I I I I I I |        ");
		System.out.println("   |  I I I I I I  |        ");
		System.out.println("   \\_             _/       ");
		System.out.println("     \\_         _/         ");
		System.out.println("       \\_______/           ");
	}

}
