# ZHReader
一款仿电子书阅读器。该阅读器实现了本地书架、从SD卡导入电子书 、语音朗读功能。阅读时具有仿真翻页、自动记录阅读进度、可设置字体大小、设置阅读亮度、设置书签、夜间日间模式切换及具有进度调整功能。
但是中文字符集有的显示乱码，还没有很好的解决，希望有熟悉的开发者，给些指点。




# epublib-core-latest.jar slf4j-android-1.6.1-RC.jar

### 获取目录

    以明朝那些事为例

    -----一级目录----: 封面
    -----一级目录----: 简介
    -----一级目录----: 前言
    -----一级目录----: 壹 洪武大帝
    -----一级目录----: 贰 万国来朝
    -----一级目录----: 叁 妖孽宫廷
    -----一级目录----: 肆 粉饰太平
    -----一级目录----: 伍 帝国飘摇
    -----一级目录----: 陆 日暮西山
    -----一级目录----: 柒 大结局
    -----一级目录----: 后记

    获取epub电子书中的目录
    TableOfContents tableOfContents = nl.siegmann.epublib.domain.Book.getTableOfContents();

    一级目录:List<TOCReference> toc = tableOfContents.getTocReferences();

        子目录：TOCReference.getChildren()




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