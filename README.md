# ZHReader
一款仿电子书阅读器。该阅读器实现了本地书架、从SD卡导入电子书 、语音朗读功能。阅读时具有仿真翻页、自动记录阅读进度、可设置字体大小、设置阅读亮度、设置书签、夜间日间模式切换及具有进度调整功能。
但是中文字符集有的显示乱码，还没有很好的解决，希望有熟悉的开发者，给些指点。



String title = book.getTitle();
            out.println(title);

            Metadata metadata = book.getMetadata();
            List<Author> authors = metadata.getAuthors();
            showAuthorDetails(authors);
            out.println("------------------------------分割线----------------------------------------");
            List<Author> contributors = metadata.getContributors();
            showAuthorDetails(authors);
            out.println("------------------------------分割线----------------------------------------");
            List<Date> dates = metadata.getDates();
            if (dates != null) {
                for (Date date : dates) {
                    out.println("--event-" + date.getEvent() + "---value--" + date.getValue());
                }
            } else {
                out.println("-------list<Data> 为空--------------------------------");
            }
            List<String> descriptions = metadata.getDescriptions();
            showString(descriptions);
            out.println("------------------------------分割线----------------------------------------");
            String firstTitle = metadata.getFirstTitle();
            out.println("-----first title----" + firstTitle);
            String format = metadata.getFormat();
            out.println("-----format----" + format);
            List<Identifier> identifiers = metadata.getIdentifiers();
            if (identifiers != null) {
                for (Identifier ids :
                        identifiers) {
                    out.println("-----scheme--" + ids.getScheme() + "--value-" + ids.getValue());
                }
            } else {
                out.println("-------List<Identifier> 为空--------------------------------");
            }
            out.println("------------------------------分割线----------------------------------------");
            String language = metadata.getLanguage();
            out.println("-----language---" + language);
            Map<QName, String> otherProperties = metadata.getOtherProperties();
            if (otherProperties != null){
                Set<QName> qNames = otherProperties.keySet();
                if (qNames != null){
                    for (QName name : qNames) {
                        out.println("------name-"+name+"------value--"+otherProperties.get(name));
                    }
                } else {
                    out.println("---------------qName为空--------------------------");
                }
            } else {
                out.println("-------otherProperties 为空--------------------------------");
            }


            List<String> publishers = metadata.getPublishers();
            showString(publishers);
            out.println("------------------------------分割线----------------------------------------");
            List<String> rights = metadata.getRights();
            showString(rights);
            out.println("------------------------------分割线----------------------------------------");
            List<String> subjects = metadata.getSubjects();
            showString(subjects);
            out.println("------------------------------分割线----------------------------------------");
            List<String> titles = metadata.getTitles();
            showString(titles);
            out.println("------------------------------分割线----------------------------------------");
            List<String> types = metadata.getTypes();
            showString(types);
            out.println("------------------------------分割线----------------------------------------");