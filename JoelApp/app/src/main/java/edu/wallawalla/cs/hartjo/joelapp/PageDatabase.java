package edu.wallawalla.cs.hartjo.joelapp;

import android.content.Context;
import android.content.res.Resources;
import java.util.ArrayList;
import java.util.List;

public class PageDatabase {

    private static PageDatabase sPageDatabase;
    private List<Page> mPages;

    public static PageDatabase getInstance(Context context) {
        if (sPageDatabase == null) {
            sPageDatabase = new PageDatabase(context);
        }
        return sPageDatabase;
    }

    private PageDatabase(Context context) {
        mPages = new ArrayList<>();
        Resources res = context.getResources();
        String[] pages = res.getStringArray(R.array.pages);
        String[] descriptions = res.getStringArray(R.array.descriptions);
        for (int i = 0; i < pages.length; i++) {
            mPages.add(new Page(i + 1, pages[i], descriptions[i]));
        }
    }

    public List<Page> getPages() {
        return mPages;
    }

    public Page getPage(int pageId) {
        for (Page page : mPages) {
            if (page.getId() == pageId) {
                return page;
            }
        }
        return null;
    }
}
