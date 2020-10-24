package com.porodem.activitysample.database;

public class IventDBSchema {
    public static final class TableEvent {
        public static final String NAME = "event";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String NOTE = "note";
            public static final String TARGET_DURATION = "target_duration";
            public static final String PREV_DUARATION = "prev_duration";
            public static final String TOP_DURATION = "top_duration";
            public static final String ICON_PATH = "icon_path";
        }
    }

    public static final class TableTrack {
        public static final String NAME = "track";

        public static final class Cols {
            public static final String ID = "id";
            public static final String EVENT_ID = "event_id";
            public static final String BDATE = "bdate";
            public static final String EDATE = "edate";
            public static final String FAIL_ID = "fail_id";
        }
    }

    public static final class TableFail {
        public static final String NAME = "fail_table";

        public static final class Cols {
            public static final String ID = "id";
            public static final String TRIGGER_NAME = "trigger_name";
        }
    }

    /*public static final class IventsTable {
        public static final String NAME = "event";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date_start";
            public static final String TOP_DURATION = "top_duration";
        }
    }*/
}
