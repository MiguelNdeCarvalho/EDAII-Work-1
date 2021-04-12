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

			int barcoX = Integer.parseInt(barcos_input[0]);
			int barcoY = Integer.parseInt(barcos_input[1]);
			int barcoRating = Integer.parseInt(barcos_input[2]);

			if (barcos_input.length != 3) return;
			if (barcoX < 1 || barcoX > 10000) return;
			if (barcoY < 1 || barcoY > 10000) return;
			if (barcoRating < 1 || barcoRating > 10000) return;

			barco.setCordenadas(barcoX, barcoY, barcoRating);
			barcos[i] = barco;
		}

		//ordenar o array de barcos do maior para o menor
		Arrays.sort(barcos, (a,b) -> {return a.rating < b.rating ? 1 : a.rating > b.rating ? -1 : 0;});

		//ler o input sobre informacoes dos peixes e guardando na class peixes as informacoes no sitio certo, verificando se esta tudo dentro dos limites		
		for (int i = 0; i < nPeixes; i++)
		{
			Peixes peixe = new Peixes();

			String[] peixes_intput = buffer.readLine().split(" ");

			int peixeX = Integer.parseInt(peixes_intput[0]);
			int peixeY = Integer.parseInt(peixes_intput[1]);
			int peixeRating = Integer.parseInt(peixes_intput[2]);

			if (peixes_intput.length != 3) return;
			if (peixeX < 1 || peixeX > 10000) return;
			if (peixeY < 1 || peixeY > 10000) return;
			if (peixeRating < 1 || peixeRating > 10000) return;

			peixe.setCordenadas(peixeX, peixeY, peixeRating);
			peixes[i] = peixe;
		}

		//ordenar o array de peixes do maior para o menor
		Arrays.sort(peixes, (a,b) -> {return a.nPeixes < b.nPeixes ? 1 : a.nPeixes > b.nPeixes ? -1 : 0;});

		System.out.println("Barcos");
		for (int i = 0; i < barcos.length; i++)
			System.out.println("X: " + barcos[i].x + " Y: " + barcos[i].y + " Rating: " + barcos[i].rating);

		System.out.println("Peixes");
		for (int i = 0; i < peixes.length; i++)
			System.out.println("X: " + peixes[i].x + " Y: " + peixes[i].y + " nPeixes: " + peixes[i].nPeixes);
	}
}