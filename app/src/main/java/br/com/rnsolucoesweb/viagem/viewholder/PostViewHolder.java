package br.com.rnsolucoesweb.viagem.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.rnsolucoesweb.viagem.R;
import br.com.rnsolucoesweb.viagem.models.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView origemView;
    public TextView destinoView;

    public PostViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.post_author);
        origemView = itemView.findViewById(R.id.post_origem);
        destinoView = itemView.findViewById(R.id.post_destino);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {

        authorView.setText(post.author);
        origemView.setText(post.origem);
        destinoView.setText(post.destino);

        //starView.setOnClickListener(starClickListener);
    }
}
