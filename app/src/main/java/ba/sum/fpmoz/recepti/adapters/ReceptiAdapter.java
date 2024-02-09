package ba.sum.fpmoz.recepti.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import ba.sum.fpmoz.recepti.EditReceptActivity;
import ba.sum.fpmoz.recepti.R;
import ba.sum.fpmoz.recepti.ReceptActivity;
import ba.sum.fpmoz.recepti.model.Recept;

public class ReceptiAdapter extends FirebaseRecyclerAdapter<Recept, ReceptiAdapter.ReceptiViewHolder > {

    public ReceptiAdapter(@NonNull FirebaseRecyclerOptions<Recept> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReceptiViewHolder holder, int position, @NonNull Recept model) {
        holder.naziv_recepta.setText(model.getNaziv());
        Glide.with(holder.receptItemImageView.getContext()).load(model.getSlika()).into(holder.receptItemImageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = getRef(holder.getAbsoluteAdapterPosition()).getKey();
                Intent i = new Intent(v.getContext(), ReceptActivity.class);
                i.putExtra("ReceptKey", key);
                v.getContext().startActivity(i);

            }
        });

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                CharSequence[] delete = {"Izbriši", "Uredi"};
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setItems(delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            getRef(holder.getAbsoluteAdapterPosition()).removeValue();
                        } else if(which == 1){
                            String key = getRef(holder.getAbsoluteAdapterPosition()).getKey();
                            Intent i = new Intent(v.getContext(), EditReceptActivity.class);
                            i.putExtra("ReceptKey", key);
                            v.getContext().startActivity(i);
                        }
                    }
                });
                alert.create().show();
                return false;
            }

        });

    }

    @NonNull
    @Override
    public ReceptiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recept_item_view, parent, false);

        return new ReceptiViewHolder(view);

    }

    class ReceptiViewHolder extends RecyclerView.ViewHolder {

        TextView naziv_recepta;
        ImageView receptItemImageView;

        TextView naziv;
        TextView sastojci;
        TextView priprema;
        ImageView receptImg;
        View view;

        public ReceptiViewHolder(@NonNull View itemView) {
            super(itemView);

            this.naziv_recepta = itemView.findViewById(R.id.recept_item_naziv);
            this.receptItemImageView = itemView.findViewById(R.id.recept_item_imageView);

            this.naziv = itemView.findViewById(R.id.nazivTxt);
            this.sastojci = itemView.findViewById(R.id.sastojci);
            this.priprema = itemView.findViewById(R.id.priprema);
            this.receptImg = itemView.findViewById(R.id.receptImageView);

            view = itemView;
        }
    }
}
