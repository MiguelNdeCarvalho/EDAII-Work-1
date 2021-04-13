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
		int nPeixes = Integer.parseInt(nB_nP[1]);

		//verificar se os valores estao dentro dos limites
		if (nBarcos < 1 || nBarcos > 4000) return;
		if (nPeixes < 1 || nPeixes > 4000) return;

		//declarar dois arrays para guardar todas as informacoes((x,y), rating/nPeixes) sobre os barcos e os peixes
		Barco[] barcos = new Barco[nBarcos];
		Peixes[] peixes = new Peixes[nPeixes];

		//ler o input sobre informacoes do barco e guardando na class barco as informacoes no sitio certo, verificando se esta tudo dentro dos limites
		for (int i = 0; i < nBarcos; i++)
		{
			Barco barco = new Barco();

			String[] barcos_input = buffer.readLine().split(" ");
			
			if (barcos_input.length != 3) return;

			barco.setCordenadas(Integer.parseInt(barcos_input[0]), Integer.parseInt(barcos_input[1]), Integer.parseInt(barcos_input[2]));

			if (barco.x < 1 || barco.x > 10000) return;
			if (barco.y < 1 || barco.y > 10000) return;
			if (barco.rating < 1 || barco.rating > 10000) return;

			barcos[i] = barco;
		}

		//ordenar o array de barcos do menor para o maior
		Arrays.sort(barcos, (a,b) -> {return a.rating < b.rating ? -1 : a.rating > b.rating ? 1 : 0;});

		//ler o input sobre informacoes dos peixes e guardando na class peixes as informacoes no sitio certo, verificando se esta tudo dentro dos limites		
		for (int i = 0; i < nPeixes; i++)
		{
			Peixes peixe = new Peixes();

			String[] peixes_intput = buffer.readLine().split(" ");
			
			if (peixes_intput.length != 3) return;

			peixe.setCordenadas(Integer.parseInt(peixes_intput[0]), Integer.parseInt(peixes_intput[1]), Integer.parseInt(peixes_intput[2]));

			if (peixe.x < 1 || peixe.x > 10000) return;
			if (peixe.y < 1 || peixe.y > 10000) return;
			if (peixe.nPeixes < 1 || peixe.nPeixes > 10000) return;

			peixes[i] = peixe;
		}

		//ordenar o array de peixes do menor para o maior
		Arrays.sort(peixes, (a,b) -> {return a.nPeixes < b.nPeixes ? -1 : a.nPeixes > b.nPeixes ? 1 : 0;});

		//objeto que guarda as informacoes sobre o total de peixes, o total das distancias e o total do rating, respetivamente
		Solution solve = new Solution();
		solve.setSolution(0, 0, 0);

		//caso o comprimento dos arrays do barco e do peixe forem iguais entao
		//calcula o numero max de peixes, a distancia percorrida e o rating dos barcos
		pesquisa(solve, barcos, peixes, barcos.length - 1, peixes.length - 1);

		//mostra o resultado
		System.out.println(solve.maxPeixes + " " + solve.dist + " " + solve.rt);
	}

	public static void pesquisa(Solution solve, Barco[] barcos, Peixes[] peixes, int pos_b, int pos_p)
	{
		if (barcos.length < peixes.length)
		{
			for (int i = pos_b, j = pos_p ; i >= 0 && j >= 0; i--)
			{
				//verifica as condicoes:
				//	- se esta no ultimo peixe
				//	- se o numero de peixes da posicao atual e maior que a posicao anterior (o array esta organizado por ordem crescente)
				//	- por ultimo verifica se a distancia da posicao atual e menor que  a anterior
				//	- como o array de barcos esta ordenado por ordem crescente a condicao do rating ser maior que a anterior nao
				//e verificada porque apenas decrementa de barco quando um peixe for atribuido
				if(j == i || j == 0 || i == 0 || (peixes[j].nPeixes >= peixes[j - 1].nPeixes || Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y) < Math.abs(barcos[i - 1].x - peixes[j].x) + Math.abs(barcos[i - 1].y - peixes[j].y) || barcos[i].rating < barcos[i - 1].rating))
				{
					//calcular o numero max de peixes
					solve.maxPeixes += peixes[j].nPeixes;
					//calcula a distancia percorrida
					solve.dist += Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y);
					//calcula o rating dos barcos
					solve.rt += barcos[i].rating;
					//passa para o proximo peixes
					j--;
				}

				else
				{
					//como nao compriu nenhuma das condicoes anteriores, entao o mesmo barco vai verificar o proximo peixe
					pesquisa(solve, barcos, peixes, i, j - 1);
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
				if(j == i || j == 0 || i == 0 || (peixes[j].nPeixes >= peixes[j - 1].nPeixes && Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y) < Math.abs(barcos[i - 1].x - peixes[j].x) + Math.abs(barcos[i - 1].y - peixes[j].y) && barcos[i].rating > barcos[i - 1].rating))
				{
					//calcular o numero max de peixes
					solve.maxPeixes += peixes[j].nPeixes;
					//calcula a distancia percorrida
					solve.dist += Math.abs(barcos[i].x - peixes[j].x) + Math.abs(barcos[i].y - peixes[j].y);
					//calcula o rating dos barcos
					solve.rt += barcos[i].rating;
					//passa para o proximo peixes
					j--;
				}

				else
				{
					//como nao compriu nenhuma das condicoes anteriores, entao o mesmo barco vai verificar o proximo peixe
					pesquisa(solve, barcos, peixes, i - 1, j);
					return;
				}
			}
		}
	}
}