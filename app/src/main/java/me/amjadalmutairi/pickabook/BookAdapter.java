package me.amjadalmutairi.pickabook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by amjadalmutairi on 7/26/17.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.singlebook, parent, false);
        }

        Book book = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(book.getTitle());

        StringBuilder authorsDateDEscStr = new StringBuilder(getContext().getString(R.string.by));
        for (int i = 0; i < book.getAuthors().size(); i++) {
            authorsDateDEscStr.append(book.getAuthors().get(i));
            authorsDateDEscStr.append(", ");
        }
        authorsDateDEscStr.append(getContext().getString(R.string.on));
        authorsDateDEscStr.append(book.getPublishedDate());
        authorsDateDEscStr.append(".");
        authorsDateDEscStr.append("\n\n" + book.getDescription());

        TextView authorsAndDate = (TextView) listItemView.findViewById(R.id.author_date_Desc);
        authorsAndDate.setText(authorsDateDEscStr);

        TextView pageCount = (TextView) listItemView.findViewById(R.id.pageCount);
        pageCount.setText(String.valueOf(book.getPageCount()) + getContext().getString(R.string.page));

        return listItemView;
    }
}