package com.rahat_jnu.expensetracker.ui.note;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rahat_jnu.expensetracker.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Note> notes;

    Adapter(Context context,List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String  title    = (String) notes.get(i).getTitle();
        String  date     = notes.get(i).getDate();
        String  time     = notes.get(i).getTime();
        long    id       = notes.get(i).getId();
        Log.d("date on ", "Date on: "+date);

        viewHolder.nTitle.setText(title);
        viewHolder.nDate.setText(date);
        viewHolder.nTime.setText(time);
        viewHolder.nID.setText(String.valueOf(notes.get(i).getId()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDate,nTime,nID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nTitle  = itemView.findViewById(R.id.nTitle);
            nDate   = itemView.findViewById(R.id.nDate);
            nTime   = itemView.findViewById(R.id.nTime);
            nID     = itemView.findViewById(R.id.listId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),Detail.class);
                    i.putExtra("ID",notes.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setTitle("Delete Note?");
                    builder1.setMessage("Are you sure want to Delete this note?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SimpleDatabase db = new SimpleDatabase(view.getContext());
                                    db.deleteNote(notes.get(getAdapterPosition()).getId());
                                    dialog.cancel();
                                    ((Activity) view.getContext()).finish();
                                    Intent i = new Intent(view.getContext(),NoteActivity.class);
                                    view.getContext().startActivity(i);
                                    Toast.makeText(view.getContext(),"Note Deleted",Toast.LENGTH_SHORT).show();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                    buttonbackground.setTextColor(Color.BLACK);

                    Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                    buttonbackground1.setTextColor(Color.BLACK);
                    return false;
                }
            });
        }
    }
}
