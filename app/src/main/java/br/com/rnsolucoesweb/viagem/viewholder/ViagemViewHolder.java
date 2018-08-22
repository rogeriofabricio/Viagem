package br.com.rnsolucoesweb.viagem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Post;
import br.com.rnsolucoesweb.viagem.models.Viagem;

public class ViagemViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView origemView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView destinoView;

    public ViagemViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.viagem_title);
        origemView = itemView.findViewById(R.id.viagem_usuario);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        destinoView = itemView.findViewById(R.id.viagem_body);
    }

    public void bindToViagem(Viagem viagem, View.OnClickListener starClickListener) {
        authorView.setText(viagem.author);
        origemView.setText(viagem.origem);
        numStarsView.setText(String.valueOf(viagem.starCount));
        destinoView.setText(viagem.destino);

        starView.setOnClickListener(starClickListener);
    }
}
