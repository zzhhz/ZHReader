package com.zzh.reader.util;

import android.util.Log;

import com.zzh.reader.model.Catalogue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * Created by Administrator.
 *
 * @date: 2018/10/10
 * @email: zzh_hz@126.com
 * @QQ: 1299234582
 * @author: zzh
 * @description: ZHReader 书籍工具类，获取目录信息（epub)
 * @since 1.0
 */
public class BookUtils {

    public static final String TAG = "-----BookUtils-----";


    public static List<Catalogue> getBookCatalogues(String path) {
        List<Catalogue> list = new ArrayList<>();
        EpubReader reader = new EpubReader();
        Book book = null;
        try {
            book = reader.readEpub(new FileInputStream(path));
            TableOfContents tableOfContents = book.getTableOfContents();
            if (tableOfContents != null) {

                List<TOCReference> toc = tableOfContents.getTocReferences();
                if (toc != null) {
                    for (TOCReference ref : toc) {
                        Catalogue catalogue = new Catalogue();
                        catalogue.setBookPath(path);
                        catalogue.setResId(ref.getResourceId());
                        catalogue.setCatalogue(ref.getTitle());
                        list.add(catalogue);
                        List<TOCReference> children = ref.getChildren();
                        if (children != null) {
                            for (TOCReference child : children) {
                                Catalogue childCatalogue = new Catalogue();
                                childCatalogue.setBookPath(path);
                                childCatalogue.setResId(child.getResourceId());
                                childCatalogue.setCatalogue(child.getTitle());
                                list.add(childCatalogue);
                            }
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


}
