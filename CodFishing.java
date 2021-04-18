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
		Item[] barcos = new Item[nBarcos];
		Item[] peixes = new Item[nPeixes];

		//ler o input sobre informacoes do barco e guardando na instancian de class barco as informacoes no sitio certo, verificando se esta tudo dentro dos limites
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

		//ler o input sobre informacoes dos peixes e guardando na instancia de class peixes as informacoes no sitio certo, verificando se esta tudo dentro dos limites		
		for (int i = 0; i < nPeixes; i++)
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

		//intancia da classe Solution que guarda o melhor caso
		Solution temp = new Solution();
		temp.setSolution(0, 0, 0);

		//matriz que guarda todas as 
		Solution[][] matriz_sol = new Solution[barcos.length][peixes.length];

		//associa um barco a cada ciclo ao local com mais peixes
		for (int i = barcos.length - 1; i >= 0; i--)
		{
			//instacia de classe para casa associacao
			Solution s = new Solution();
			Solution s1 = new Solution();

			//verifica se o a associacao ja se encontra calcula (isto e, com o melhor caso)
			//caso nao estaja entra no if, caso esteja entra no else para copiar os valores do melhor caso
			if (matriz_sol[i][peixes.length - 1] == null)
				s.copy(pesquisa(matriz_sol, barcos, peixes, i, peixes.length - 1));

			else
				s.copy(matriz_sol[i][peixes.length - 1]);

			System.out.println("ARRAY");
				for (int j = 0; j < barcos.length; j++)
					System.out.println(barcos[j].x);

			if (i > 0 && barcos[i].atributo == barcos[i - 1].atributo)
			{
				System.out.println("fora do pesquisa barco " + i + " com rating " + barcos[i].atributo);
				System.out.println("fora do pesquisa barco " + (i -1) + " com rating " + barcos[i -1].atributo);
				Item aux = barcos[i];
				barcos[i] = barcos[i - 1];
				barcos[i - 1] = aux;

				System.out.println("mostrar troca antes");
				for (int j = 0; j < barcos.length; j++)
					System.out.println(barcos[j].x);

				s1.copy(pesquisa(matriz_sol, barcos, peixes, i, peixes.length -1));

				aux = barcos[i];
				barcos[i] = barcos[i - 1];
				barcos[i - 1] = aux;

				System.out.println("mostrar troca depois");
				for (int j = 0; j < barcos.length; j++)
					System.out.println(barcos[j].x);

				if (s.maxPeixes <= s1.maxPeixes && s.dist >= s1.dist && s.rating >= s1.rating)
					s.copy(s1);
			}

			System.out.println("terminou a pesquisa da main do barco " + i + " com o S: " + s.maxPeixes + " " + s.dist + " " + s.rating);

			System.out.println("antes do if temp: " + temp.maxPeixes + " " + temp.dist + " " + temp.rating);

			//verifica os 3 caso pertendidos, ter o maximo de peixes, ter o minimo de distancia e ter um rating menor, a ultima verificacao e apenas para entrar na primeira vez
			if (temp.maxPeixes <= s.maxPeixes && temp.dist >= s.dist && temp.rating >= s.rating || i == (barcos.length - 1))
			{
				temp.copy(s);
				System.out.println("temp atualizado: " + temp.maxPeixes + " " + temp.dist + " " + temp.rating);
			}
		}
		
		//mostra o melhor caso
		System.out.println(temp.maxPeixes + " " + temp.dist + " " + temp.rating);
	}

	public static Solution pesquisa(Solution[][] matriz_sol, Item[] barcos, Item[] peixes, int pos_b, int pos_p)
	{
		//System.out.println(pos_b + " " + pos_p);
		//caso base, quando nao tem mais barcos para serem atribuidos ou nao tem mais sitio de peixes para atribuir
		if (pos_b < 0 || pos_p < 0) 
		{
			//cria uma instancia da classe Solution para que o pai possa altera-lo (maxPeixes, dist, rating)
			Solution temp = new Solution();
			temp.setSolution(0, 0, 0);

			return temp;
		}

		//verifica se o a associacao ja se encontra calcula (isto e, com o melhor caso)
		//caso nao estaja entra no if, caso esteja entra no else para copiar os valores do melhor caso
		/*if (matriz_sol[pos_b][pos_p] == null || pos_b > 0 && barcos[pos_b].atributo == barcos[pos_b - 1].atributo)
		{*/
			//calcula qual o melhor caso do lado esquerdo da arvore
			Solution s1 = new Solution();
			System.out.println("1ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 1));
			s1.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 1, pos_p - 1));
			System.out.println("terminou a 1ª pesquisa do barco " + pos_b + " com o S1: " + s1.maxPeixes + " " + s1.dist + " " + s1.rating);

			//incrementa o numero max de peixes
			//incrementa a distancia percorrida usando a distancia de manhattan
			//incrementa o atributo dos barcos
			s1.inc(barcos[pos_b], peixes[pos_p]);

			System.out.println("apos incremento do barco " + pos_b + " o S1: " + s1.maxPeixes + " " + s1.dist + " " + s1.rating);

			//criar uma instancia para saber qual o melhor caso do lado direito da arvore
			Solution s2 = new Solution();
			System.out.println("2ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 2));
			s2.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 2, pos_p - 1));
			System.out.println("terminou a 2ª pesquisa do barco " + pos_b + " com o S2: " + s2.maxPeixes + " " + s2.dist + " " + s2.rating);

			//incrementa o numero max de peixes
			//incrementa a distancia percorrida
			//incrementa o atributo dos barcos
			s2.inc(barcos[pos_b], peixes[pos_p]);

			System.out.println("apos incremento do barco " + pos_b + " o S2: " + s2.maxPeixes + " " + s2.dist + " " + s2.rating);

			System.out.println("\nantes\nS1: " + s1.maxPeixes + " " + s1.dist + " " + s1.rating);
			System.out.println("S2: " + s2.maxPeixes + " " + s2.dist + " " + s2.rating);

			//verifica se o lado esquerdo tem mais peixes que o lado direito, ou
			// se o lado esquerdo tem uma distancia menor que o lado direito, ou
			// se o lado esquerdo tem um rating menor que o lado direito
			//tem de ser por esta ordem pois o maxPeixes pervalece sobre a dist e o dist pervalece sobre o rating
			//caso cumpra uma comparacao entra no if, caso contrario entra no else
			if(s1.maxPeixes > s2.maxPeixes || s1.dist < s2.dist || s1.rating < s2.rating)
			{
				if(matriz_sol[pos_b][pos_p] == null || matriz_sol[pos_b][pos_p].maxPeixes > s1.maxPeixes || matriz_sol[pos_b][pos_p].dist < s1.dist || matriz_sol[pos_b][pos_p].rating < s1.rating)
				{
					matriz_sol[pos_b][pos_p] = s1;
				}
			}

			else
			{
				if(matriz_sol[pos_b][pos_p] == null || matriz_sol[pos_b][pos_p].maxPeixes > s2.maxPeixes || matriz_sol[pos_b][pos_p].dist < s2.dist || matriz_sol[pos_b][pos_p].rating < s2.rating)
				{
					matriz_sol[pos_b][pos_p] = s2;
				}
			}	

				

			/*for (int i = 0; i < barcos.length; i++)
				System.out.println(barcos[i].x);*/

			if (pos_b > 1 && barcos[pos_b - 1].atributo == barcos[pos_b - 2].atributo)
			{
				System.out.println("dentro da pesquisa");
				for (int i = 0; i < barcos.length; i++)
					System.out.println(barcos[i].x);

				System.out.println("dentro do pesquisa barco " + (pos_b - 1) + " com rating " + barcos[pos_b - 1].atributo);
				System.out.println("dentro do pesquisa barco " + (pos_b - 2) + " com rating " + barcos[pos_b - 2].atributo);
				Item aux_pesquisa = barcos[pos_b - 1];
				barcos[pos_b - 1] = barcos[pos_b - 2];
				barcos[pos_b - 2] = aux_pesquisa;

				Solution s3 = new Solution();
				System.out.println("1ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 1));
				s3.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 1, pos_p - 1));
				System.out.println("terminou a 1ª pesquisa do barco " + pos_b + " com o S1: " + s3.maxPeixes + " " + s3.dist + " " + s3.rating);

				s3.inc(barcos[pos_b], peixes[pos_p]);
				System.out.println("apos incremento do barco " + pos_b + " o S1: " + s3.maxPeixes + " " + s3.dist + " " + s3.rating);
				if (s3.maxPeixes > matriz_sol[pos_b][pos_p].maxPeixes || s3.dist < matriz_sol[pos_b][pos_p].dist || s3.rating < matriz_sol[pos_b][pos_p].rating)
					matriz_sol[pos_b][pos_p] = s3;

				Solution s4 = new Solution();

				System.out.println("2ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 2));
				s4.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 2, pos_p - 1));
				System.out.println("terminou a 2ª pesquisa do barco " + pos_b + " com o S2: " + s4.maxPeixes + " " + s4.dist + " " + s4.rating);
				s4.inc(barcos[pos_b], peixes[pos_p]);

				System.out.println("apos incremento do barco " + pos_b + " o S2: " + s4.maxPeixes + " " + s4.dist + " " + s4.rating);

				System.out.println("\nantes\nS3: " + s3.maxPeixes + " " + s3.dist + " " + s3.rating);
				System.out.println("S4: " + s4.maxPeixes + " " + s4.dist + " " + s4.rating);

				if (s4.maxPeixes > matriz_sol[pos_b][pos_p].maxPeixes || s4.dist < matriz_sol[pos_b][pos_p].dist || s4.rating < matriz_sol[pos_b][pos_p].rating)
					matriz_sol[pos_b][pos_p] = s4;

				aux_pesquisa = barcos[pos_b - 1];
				barcos[pos_b - 1] = barcos[pos_b - 2];
				barcos[pos_b - 2] = aux_pesquisa;
			}
		//}


		//System.out.println("arr_sol[" + pos_b + "][" + pos_p + "]: " + matriz_sol[pos_b][pos_p].maxPeixes + " " + matriz_sol[pos_b][pos_p].dist + " " + matriz_sol[pos_b][pos_p].rating);

		//devolve o melhor caso para este subproblema
		return matriz_sol[pos_b][pos_p];
	}
}