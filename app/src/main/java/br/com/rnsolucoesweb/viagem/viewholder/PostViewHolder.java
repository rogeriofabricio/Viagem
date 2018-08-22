package br.com.rnsolucoesweb.viagem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    //Viagem
    public TextView origemView;
    public TextView destinoView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.post_num_stars);
        bodyView = itemView.findViewById(R.id.post_body);

        //Viagem
        origemView = itemView.findViewById(R.id.post_origem);
        destinoView = itemView.findViewById(R.id.post_destino);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        numStarsView.setText(String.valueOf(post.starCount));
        bodyView.setText(post.body);

        //Viagem
        origemView.setText(post.origem);
        destinoView.setText(post.destino);

        starView.setOnClickListener(starClickListener);
    }
}
