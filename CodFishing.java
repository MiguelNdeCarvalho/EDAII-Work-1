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

		//objeto que guarda as informacoes sobre o total de peixes, o total das distancias e o total do rating, respetivamente
		/*Solution solve = new Solution();
		solve.setSolution(0, Integer.MAX_VALUE, 0);*/

		/*if (barcos.length < peixes.length) {solve = new Item[peixes.length];}
		else {solve = new Item[barcos.length];}*/

		Solution temp = new Solution();
		temp.setSolution(0, 0, 0);

		Solution[][] matriz_sol = new Solution[barcos.length][peixes.length];

		//caso o comprimento dos arrays do barco e do peixe forem iguais entao
		//calcula o numero max de peixes, a distancia percorrida e o rating dos barcos
		for (int i = 1; i <= barcos.length; i++)
		{
			Solution s = new Solution();
			if(matriz_sol[barcos.length - i][peixes.length - 1] == null)
				s.copy(pesquisa(matriz_sol, barcos, peixes, barcos.length - i, peixes.length - 1));

			else
				s.copy(matriz_sol[barcos.length - i][peixes.length - 1]);


			//System.out.println("terminou a pesquisa da main do barco " + (barcos.length-i) + " com o S: " + s.maxPeixes + " " + s.dist + " " + s.rating);

			//System.out.println("antes do if temp: " + temp.maxPeixes + " " + temp.dist + " " + temp.rating);

			if (temp.maxPeixes <= s.maxPeixes && temp.dist >= s.dist && temp.rating >= s.rating || i == 1)
			{
				temp.copy(s);
				//System.out.println("temp: " + temp.maxPeixes + " " + temp.dist + " " + temp.rating);
			}
		}
		

		//mostra o resultado
		System.out.println(temp.maxPeixes + " " + temp.dist + " " + temp.rating);
	}

	public static Solution pesquisa(Solution[][] matriz_sol, Item[] barcos, Item[] peixes, int pos_b, int pos_p)
	{
		Solution temp = new Solution();
		temp.setSolution(0, 0, 0);

		if (pos_b < 0 || pos_p < 0) {/*System.out.println("saiu porque " + pos_b + "<0 ou porque " + pos_p + "<0");*/ return temp;}

		Solution s1 = new Solution();
		//System.out.println("1ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 1));
		if (matriz_sol[pos_b][pos_p] == null)
		{
			s1.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 1, pos_p - 1));
		
			//System.out.println("terminou a 1ª pesquisa do barco " + pos_b + " com o S1: " + s1.maxPeixes + " " + s1.dist + " " + s1.rating);

			//calcular o numero max de peixes
			s1.maxPeixes += peixes[pos_p].atributo;
			//calcula a distancia percorrida
			s1.dist += Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y);
			//calcula o atributo dos barcos
			s1.rating += barcos[pos_b].atributo;
			//passa para o proximo peixes

			Solution s2 = new Solution();
			//System.out.println("2ª pesquisa: entrou com o barco " + pos_b + " e vai para o barco " + (pos_b - 2));
			s2.copy(pesquisa(matriz_sol, barcos, peixes, pos_b - 2, pos_p - 1));
			//System.out.println("terminou a 2ª pesquisa do barco " + pos_b + " com o S1: " + s2.maxPeixes + " " + s2.dist + " " + s2.rating);

			//calcular o numero max de peixes
			s2.maxPeixes += peixes[pos_p].atributo;
			//calcula a distancia percorrida
			s2.dist += Math.abs(barcos[pos_b].x - peixes[pos_p].x) + Math.abs(barcos[pos_b].y - peixes[pos_p].y);
			//calcula o atributo dos barcos
			s2.rating += barcos[pos_b].atributo;
			//passa para o proximo peixes


			//System.out.println("\nantes\nS1: " + s1.maxPeixes + " " + s1.dist + " " + s1.rating);
			//System.out.println("S2: " + s2.maxPeixes + " " + s2.dist + " " + s2.rating);
			if(s1.maxPeixes > s2.maxPeixes || s1.dist < s2.dist || s1.rating < s2.rating)
				matriz_sol[pos_b][pos_p] = s1;

			else
				matriz_sol[pos_b][pos_p] = s2;

			//System.out.println("depois\ntemp: " + temp.maxPeixes + " " + temp.dist + " " + temp.rating);
		}

		//System.out.println("arr_sol[" + pos_b + "][" + pos_p + "]: " + matriz_sol[pos_b][pos_p].maxPeixes + " " + matriz_sol[pos_b][pos_p].dist + " " + matriz_sol[pos_b][pos_p].rating);

		return matriz_sol[pos_b][pos_p];
	}
}