import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CodFishing
{
	public static void main(String[] args) throws IOException
	{
		//declarar um buffer para puder ler inputs
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		//ler uma String com dois valores, numero de barcos e numeros de peixes
		String[] nB_nP = buffer.readLine().split(" ");

		//verificar se o input tem exatamente dois valores
		if (nB_nP.length != 2) return;

		//transformar os dois valores do input em inteiros e guardar em variaveis
		int nBarcos = Integer.parseInt(nB_nP[0]);
		int atributo = Integer.parseInt(nB_nP[1]);

		//verificar se os valores estao dentro dos limites
		if (nBarcos < 1 || nBarcos > 4000) return;
		if (atributo < 1 || atributo > 4000) return;

		//declarar dois arrays para guardar todas as informacoes((x,y), rating/nPeixes) sobre os barcos e os peixes
		Item[] barcos = new Item[nBarcos];
		Item[] peixes = new Item[atributo];

		//ler o input sobre informacoes do barco e guardando na class barco as informacoes no sitio certo, verificando se esta tudo dentro dos limites
		for (int i = 0; i < nBarcos; i++)
		{
			Item barco = new Item();

			String[] barcos_input = buffer.readLine().split(" ");
			
			if (barcos_input.length != 3) return;

			barco.setCordenadas(Integer.parseInt(barcos_input[0]), Integer.parseInt(barcos_input[1]), Integer.parseInt(barcos_input[2]));

			if (barco.x < 1 || barco.x > 10000) return;
			if (barco.y < 1 || barco.y > 10000) return;
			if (barco.atributo < 1 || barco.atributo > 10000) return;

			barcos[i] = barco;
		}

		//ordenar o array de barcos do menor para o maior
		Arrays.sort(barcos, (a,b) -> {return a.atributo < b.atributo ? -1 : a.atributo > b.atributo ? 1 : 0;});

		//ler o input sobre informacoes dos peixes e guardando na class peixes as informacoes no sitio certo, verificando se esta tudo dentro dos limites		
		for (int i = 0; i < atributo; i++)
		{
			Item peixe = new Item();

			String[] peixes_intput = buffer.readLine().split(" ");
			
			if (peixes_intput.length != 3) return;

			peixe.setCordenadas(Integer.parseInt(peixes_intput[0]), Integer.parseInt(peixes_intput[1]), Integer.parseInt(peixes_intput[2]));

			if (peixe.x < 1 || peixe.x > 10000) return;
			if (peixe.y < 1 || peixe.y > 10000) return;
			if (peixe.atributo < 1 || peixe.atributo > 10000) return;

			peixes[i] = peixe;
		}

		//ordenar o array de peixes do menor para o maior
		Arrays.sort(peixes, (a,b) -> {return a.atributo < b.atributo ? -1 : a.atributo > b.atributo ? 1 : 0;});

		//objeto que guarda as informacoes sobre o total de peixes, o total das distancias e o total do rating, respetivamente
		Solution solve = new Solution();
		solve.setSolution(0, 0/*Integer.MAX_VALUE*/, 0);

		/*if (barcos.length < peixes.length) {solve = new Item[peixes.length];}
		else {solve = new Item[barcos.length];}*/

		Solution temp = new Solution();
		temp.setSolution(0, 0, 0);

		//caso o comprimento dos arrays do barco e do peixe forem iguais entao
		//calcula o numero max de peixes, a distancia percorrida e o rating dos barcos
		/*temp = pesquisa(solve, temp, barcos, peixes, barcos.length - 1, peixes.length - 1);

		//mostra o resultado
		System.out.println(temp.maxPeixes + " " + temp.dist + " " + temp.rating);*/

		
		pesquisa_experimento(solve, barcos, peixes, barcos.length - 1, peixes.length - 1);

		//mostra o resultado
		System.out.println(solve.maxPeixes + " " + solve.dist + " " + solve.rating);

		
		/*pesquisaDinamica(solve, barcos, peixes, pos_b, pos_p);

		//mostra o resultado
		System.out.println(solve.maxPeixes + " " + solve.dist + " " + solve.rating);*/
	}

	public static Solution pesquisa(Solution solve, Solution temp, Item[] barcos, Item[] peixes, int pos_b, int pos_p)
	{
		if (pos_p < 0 || pos_b < 0)
			{
				System.out.println("entrou");
				Solution temporario = new Solution();
				temporario.setSolution(0, 0, 0);
				return temporario;
			}

		if (barcos.length >= peixes.length)
		{
			for (int  i = pos_b; i >= 0; i--)
			{
				temp.maxPeixes = peixes[pos_p].atributo;
				temp.dist = Math.abs(barcos[i].x - peixes[pos_p].x) + Math.abs(barcos[i].y - peixes[pos_p].y);
				temp.rating = barcos[i].atributo;

				//solve[pos_p] = barcos[i];

				Solution aux ;
				aux = pesquisa(solve, temp, barcos, peixes, i - 1, pos_p - 1);

				temp.maxPeixes += aux.maxPeixes;
				temp.dist += aux.dist;
				temp.rating += aux.rating;

				System.out.println("barco: " + i + " peixe: " + pos_p + " temp.dist: " + temp.dist + " solve.dist: " + solve.dist);

				if(solve.dist > temp.dist)
				{
					System.out.println("entrou na comparacao");
					solve.maxPeixes = temp.maxPeixes;
					solve.dist = temp.dist;
					solve.rating = temp.rating;
				}

				

			}
		}

		return temp;
	}





















	public static void pesquisa_experimento(Solution solve, Item[] barcos, Item[] peixes, int pos_b, int pos_p)
	{
		if (barcos.length <= peixes.length)
		{
			for (int i = pos_b, j = pos_p ; i >= 0 && j >= 0; i--)
			{
				//verifica as condicoes:
				//	- se esta no ultimo peixe
				//	- se o numero de peixes da posicao atual e maior que a posicao anterior (o array esta organizado por ordem crescente)
				//	- por ultimo verifica se a distancia da posicao atual e menor que  a anterior
				//	- como o array de barcos esta ordenado por ordem crescente a condicao do rating ser maior que a anterior nao
				//e verificada porque apenas decrementa de barco quando um peixe for atribuido
				if(j == i || j == 0 || i == 0 || (peixes[j].atributo >= peixes[j - 1].atributo || Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y) < Math.abs(barcos[i - 1].x - peixes[j].x) + Math.abs(barcos[i - 1].y - peixes[j].y) || barcos[i].atributo < barcos[i - 1].atributo))
				{
					//calcular o numero max de peixes
					solve.maxPeixes += peixes[j].atributo;
					//calcula a distancia percorrida
					solve.dist += Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y);
					//calcula o atributo dos barcos
					solve.rating += barcos[i].atributo;
					//passa para o proximo peixes
					j--;
				}

				else
				{
					//como nao compriu nenhuma das condicoes anteriores, entao o mesmo barco vai verificar o proximo peixe
					pesquisa_experimento(solve, barcos, peixes, i, j - 1);
					return;
				}
			}
		}

		else
		{
			for (int i = pos_b, j = pos_p ; i >= 0 && j >= 0; i--)
			{
				//verifica as condicoes:
				//	- se esta no ultimo peixe
				//	- se o numero de peixes da posicao atual e maior que a posicao anterior (o array esta organizado por ordem crescente)
				//	- por ultimo verifica se a distancia da posicao atual e menor que  a anterior
				//	- como o array de barcos esta ordenado por ordem crescente a condicao do rating ser maior que a anterior nao
				//e verificada porque apenas decrementa de barco quando um peixe for atribuido
				if(j == i || j == 0 || i == 0 || (peixes[j].atributo >= peixes[j - 1].atributo && Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y) < Math.abs(barcos[i - 1].x - peixes[j].x) + Math.abs(barcos[i - 1].y - peixes[j].y) && barcos[i].atributo > barcos[i - 1].atributo))
				{
					//calcular o numero max de peixes
					solve.maxPeixes += peixes[j].atributo;
					//calcula a distancia percorrida
					solve.dist += Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y);
					//calcula o rating dos barcos
					solve.rating += barcos[i].atributo;
					//passa para o proximo peixes
					j--;
				}

				else
				{
					//como nao compriu nenhuma das condicoes anteriores, entao o mesmo barco vai verificar o proximo peixe
					pesquisa_experimento(solve, barcos, peixes, i - 1, j);
					return;
				}
			}
		}
	}

	public static void pesquisaDinamica(Solution solve, Item[] barcos, Item[] peixes, int pos_b, int pos_p)
	{
		if (pos_p > pos_b && pos_p > 0 && pos_b > 0)
		{
			/*System.out.println("primeiro if barcos: " + barcos[pos_b].atributo);
			System.out.println(peixes[pos_p].atributo >= peixes[pos_p - 1].atributo);
			System.out.println(Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y) <= Math.abs(barcos[pos_b - 1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b - 1].y - peixes[pos_p].y));
			//System.out.println(Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y));
			//System.out.println(Math.abs(barcos[pos_b-1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b-1].y - peixes[pos_p].y));
			System.out.println(barcos[pos_b].atributo >= barcos[pos_b - 1].atributo);*/
			if (peixes[pos_p].atributo >= peixes[pos_p - 1].atributo || Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y) <= Math.abs(barcos[pos_b - 1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b - 1].y - peixes[pos_p].y) || barcos[pos_b].atributo >= barcos[pos_b - 1].atributo)
			{
				//System.out.println("Entrou");
				//calcular o numero max de peixes
				solve.maxPeixes += peixes[pos_p].atributo;
				//calcula a distancia percorrida
				solve.dist += Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y);
				//calcula o rating dos barcos
				solve.rating += barcos[pos_b].atributo;
				//passa para o proximo peixes
				pesquisaDinamica(solve, barcos, peixes, pos_b - 1, pos_p - 1);
			}

			else pesquisaDinamica(solve, barcos, peixes, pos_b, pos_p - 1);
		}

		else if (pos_b > pos_p && pos_p > 0 && pos_b > 0)
		{
			/*System.out.println("segunda if barcos: " + barcos[pos_b].rating);
			System.out.println(peixes[pos_p].nPeixes >= peixes[pos_p - 1].nPeixes);
			System.out.println(Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y) <= Math.abs(barcos[pos_b - 1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b - 1].y - peixes[pos_p].y));
			System.out.println(Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y));
			System.out.println(Math.abs(barcos[pos_b-1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b-1].y - peixes[pos_p].y));
			System.out.println(barcos[pos_b].rating >= barcos[pos_b - 1].rating);*/
			if (peixes[pos_p].atributo >= peixes[pos_p - 1].atributo && Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y) <= Math.abs(barcos[pos_b - 1].x - peixes[pos_p].x) + Math.abs(barcos[pos_b - 1].y - peixes[pos_p].y) && barcos[pos_b].atributo >= barcos[pos_b - 1].atributo)
			{
				//System.out.println("Entrou");
				//calcular o numero max de peixes
				solve.maxPeixes += peixes[pos_p].atributo;
				//calcula a distancia percorrida
				solve.dist += Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y);
				//calcula o rating dos barcos
				solve.rating += barcos[pos_b].atributo;
				//passa para o proximo peixes
				pesquisaDinamica(solve, barcos, peixes, pos_b - 1, pos_p - 1);
			}

			else pesquisaDinamica(solve, barcos, peixes, pos_b -1, pos_p);
		}

		else if (pos_b >= 0 && pos_p >= 0)
		{
			//System.out.println("ultimo if barcos: " + barcos[pos_b].rating);
			//calcular o numero max de peixes
			solve.maxPeixes += peixes[pos_p].atributo;
			//calcula a distancia percorrida
			solve.dist += Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y);
			//calcula o rating dos barcos
			solve.rating += barcos[pos_b].atributo;
			//passa para o proximo peixes
			pesquisaDinamica(solve, barcos, peixes, pos_b - 1, pos_p - 1);
		}
	}
}