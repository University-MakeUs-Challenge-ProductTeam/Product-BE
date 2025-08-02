create table branch
(
    semester   int          not null,
    branch_id  bigint auto_increment
        primary key,
    created_at datetime(6)  null,
    deleted_at datetime(6)  null,
    updated_at datetime(6)  null,
    name       varchar(255) not null
);

create table file
(
    created_at       datetime(6)  null,
    deleted_at       datetime(6)  null,
    id               bigint       not null
        primary key,
    updated_at       datetime(6)  null,
    file_name        varchar(255) null,
    file_origin_name varchar(255) null,
    file_path        varchar(255) null,
    file_type        varchar(255) null
);

create table file_seq
(
    next_val bigint null
);

create table invite_code
(
    type        tinyint        null,
    created_at  datetime(6)    null,
    deleted_at  datetime(6)    null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)    null,
    code        varchar(255)   null,
    expire_date varbinary(255) null
);

create table invite_code_roles
(
    invite_code_id bigint                                                                                                     not null,
    roles          enum ('ADMIN', 'BRANCH_STAFF', 'CENTRAL_ADMIN', 'CHALLENGER', 'GUEST', 'SCHOOL_ADMIN', 'UNIVERSITY_STAFF') null,
    constraint FKo4peg1o5ukes23yptv9wo6fxe
        foreign key (invite_code_id) references invite_code (id)
);

create table member
(
    role          int                                                                 not null,
    created_at    datetime(6)                                                         null,
    deleted_at    datetime(6)                                                         null,
    id            bigint auto_increment
        primary key,
    university_id bigint                                                              null,
    updated_at    datetime(6)                                                         null,
    avatar_url    varchar(255)                                                        null,
    email         varchar(255)                                                        null,
    name          varchar(255)                                                        null,
    nick_name     varchar(255)                                                        null,
    login_type    enum ('ANONYMOUS', 'APPLE', 'GOOGLE', 'INTERNAL', 'KAKAO', 'NAVER') null,
    status        enum ('ACTIVE', 'DELETED', 'OLD', 'OUT', 'WAITING_FOR_UPDATE')      null
);

create table member_login_info
(
    created_at      datetime(6)  null,
    deleted_at      datetime(6)  null,
    id              bigint auto_increment
        primary key,
    member_id       bigint       null,
    updated_at      datetime(6)  null,
    member_login_id varchar(255) null,
    password        varchar(255) null,
    constraint UKlf8ydgsd9kmdlc6l2x46vhiju
        unique (member_id),
    constraint FKrnay5rbn7l1uy3mal3lyc92cm
        foreign key (member_id) references member (id)
);

create table member_out
(
    created_at datetime(6)                                                                                                                            null,
    deleted_at datetime(6)                                                                                                                            null,
    id         bigint auto_increment
        primary key,
    member_id  bigint                                                                                                                                 null,
    updated_at datetime(6)                                                                                                                            null,
    out_reason enum ('NOTICE_CHECK_NOT_PERFORM', 'NO_SHOW', 'PROJECT_ASSIGNMENT_NOT_PERFORM', 'PROJECT_CHECK_NOT_PERFORM', 'STUDY_CHECK_NOT_PERFORM') null,
    constraint FKcifitsn0prbc8wg9wnd09psa8
        foreign key (member_id) references member (id)
);

create table project
(
    end_date       date                              null,
    publish_status bit                               not null,
    start_date     date                              not null,
    branch_id      bigint                            not null,
    created_at     datetime(6)                       null,
    deleted_at     datetime(6)                       null,
    id             bigint auto_increment
        primary key,
    updated_at     datetime(6)                       null,
    description    varchar(1000)                     null,
    image_url      varchar(1000)                     null,
    logo_url       varchar(1000)                     null,
    publish_link   varchar(255)                      null,
    slogan         varchar(255)                      null,
    title          varchar(255)                      not null,
    prize          enum ('FIRST', 'SECOND', 'THIRD') null,
    constraint FKiysrpckv8iw454brxy5tin6gg
        foreign key (branch_id) references branch (branch_id)
);

create table member_project
(
    created_at datetime(6) null,
    deleted_at datetime(6) null,
    id         bigint auto_increment
        primary key,
    member_id  bigint      not null,
    project_id bigint      not null,
    updated_at datetime(6) null,
    constraint FKl2brpp0how3olc7qjtqyrb207
        foreign key (project_id) references project (id),
    constraint FKp4v2smu74i6vrd6ek1b3o2755
        foreign key (member_id) references member (id)
);

create table member_project_part
(
    created_at        datetime(6)                                                        null,
    deleted_at        datetime(6)                                                        null,
    id                bigint auto_increment
        primary key,
    member_project_id bigint                                                             not null,
    updated_at        datetime(6)                                                        null,
    part              enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') not null,
    constraint FK308jbkbhh3c2kau99ixf19053
        foreign key (member_project_id) references member_project (id)
);

create table project_part
(
    created_at datetime(6)                                                        null,
    deleted_at datetime(6)                                                        null,
    id         bigint auto_increment
        primary key,
    project_id bigint                                                             not null,
    updated_at datetime(6)                                                        null,
    part       enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') not null,
    constraint FK31g7awsfdb6f1dx5oqx37egka
        foreign key (project_id) references project (id)
);

create table roadmap
(
    week       int                                                                not null,
    created_at datetime(6)                                                        null,
    deleted_at datetime(6)                                                        null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)                                                        null,
    part       enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') null
);

create table roadmap_title
(
    created_at datetime(6)  null,
    deleted_at datetime(6)  null,
    id         bigint auto_increment
        primary key,
    roadmap_id bigint       not null,
    updated_at datetime(6)  null,
    title      varchar(255) not null,
    constraint FKmusmbghd1ebas7wlhmuokvh7x
        foreign key (roadmap_id) references roadmap (id)
);

create table semester
(
    created_at datetime(6)  null,
    deleted_at datetime(6)  null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)  null,
    name       varchar(255) null
);

create table event
(
    event_type       tinyint      null,
    max_participants int          null,
    created_at       datetime(6)  null,
    deleted_at       datetime(6)  null,
    event_end_date   datetime(6)  null,
    event_start_date datetime(6)  null,
    id               bigint auto_increment
        primary key,
    semester_id      bigint       not null,
    updated_at       datetime(6)  null,
    writer_id        bigint       not null,
    content          varchar(255) null,
    location         varchar(255) null,
    title            varchar(255) null,
    constraint FKc2dqk5gjqvvrrlqqffbh2oarc
        foreign key (writer_id) references member (id),
    constraint FKt6mrv8r1rds4w6vs0ajqtgtd6
        foreign key (semester_id) references semester (id)
);

create table event_form
(
    created_at  datetime(6)  null,
    deleted_at  datetime(6)  null,
    event_id    bigint       not null,
    id          bigint auto_increment
        primary key,
    updated_at  datetime(6)  null,
    description varchar(255) null,
    form_title  varchar(255) not null,
    constraint UK666a55yxaom5fu8h8dk7lqv54
        unique (event_id),
    constraint FK87qdo86lhudc061krn9hs4t6g
        foreign key (event_id) references event (id)
);

create table event_form_question
(
    question_order   int                          null,
    created_at       datetime(6)                  null,
    deleted_at       datetime(6)                  null,
    event_form_id    bigint                       not null,
    id               bigint auto_increment
        primary key,
    updated_at       datetime(6)                  null,
    question_content varchar(255)                 null,
    question_title   varchar(255)                 not null,
    response_type    enum ('FILE_UPLOAD', 'TEXT') null,
    constraint FKhwf1lcionxebum1klakpuvq14
        foreign key (event_form_id) references event_form (id)
);

create table event_images
(
    event_id  bigint       not null,
    image_url varchar(255) null,
    constraint FKbf173wtth5u1u7ttu9jeignci
        foreign key (event_id) references event (id)
);

create table event_registration_settings
(
    cancellation_deadline   datetime(6) null,
    created_at              datetime(6) null,
    deleted_at              datetime(6) null,
    event_id                bigint      not null,
    id                      bigint auto_increment
        primary key,
    registration_end_date   datetime(6) null,
    registration_start_date datetime(6) null,
    updated_at              datetime(6) null,
    constraint UK47gags0gwpuon6layy09p17pf
        unique (event_id),
    constraint FK17nf4xwqco2anu5h6cplwqw50
        foreign key (event_id) references event (id)
);

create table event_allowed_parts
(
    registration_settings_id bigint                                                             not null,
    allowed_part_list        enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') null,
    constraint FKccqnv3uvtj12m6aw1726ddrwo
        foreign key (registration_settings_id) references event_registration_settings (id)
);

create table event_allowed_roles
(
    registration_settings_id bigint                                                                                                     not null,
    allowed_role_list        enum ('ADMIN', 'BRANCH_STAFF', 'CENTRAL_ADMIN', 'CHALLENGER', 'GUEST', 'SCHOOL_ADMIN', 'UNIVERSITY_STAFF') null,
    constraint FK7cr7w3strwwwn5dle15kywmgi
        foreign key (registration_settings_id) references event_registration_settings (id)
);

create table notice
(
    created_at     datetime(6)                              null,
    deleted_at     datetime(6)                              null,
    notice_id      bigint auto_increment
        primary key,
    updated_at     datetime(6)                              null,
    content        varchar(255)                             null,
    title          varchar(255)                             null,
    check_deadline datetime(6)                              null,
    hashtags       varchar(255)                             null,
    images         varchar(255)                             null,
    notice_date    datetime(6)                              not null,
    target         enum ('BRANCH', 'CENTRAL', 'UNIVERSITY') not null,
    event_id       bigint                                   null,
    writer_id      bigint                                   not null,
    constraint UKgjox613o9ax8lb6101t94j2iq
        unique (event_id),
    constraint FKpgv4s3eisgwwxc49ch7m27c9j
        foreign key (event_id) references event (id),
    constraint FKs55v5bheh81cb5s0bfvk104td
        foreign key (writer_id) references member (id)
);

create table notice_member
(
    is_checked       bit         not null,
    is_read          bit         not null,
    created_at       datetime(6) null,
    deleted_at       datetime(6) null,
    member_id        bigint      not null,
    notice_id        bigint      not null,
    notice_member_id bigint auto_increment
        primary key,
    updated_at       datetime(6) null,
    constraint FK1mr5wq9ql3p56adbme0jxq2nl
        foreign key (notice_id) references notice (notice_id),
    constraint FKd5xajwq6a1egpa22b73m4dl24
        foreign key (member_id) references member (id)
);

create table notice_part
(
    id         bigint auto_increment
        primary key,
    created_at datetime(6)                                                        null,
    deleted_at datetime(6)                                                        null,
    updated_at datetime(6)                                                        null,
    part       enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') not null,
    notice_id  bigint                                                             null,
    constraint FKsga247ydxgial5pp55sircwff
        foreign key (notice_id) references notice (notice_id)
);

create table notice_semester
(
    id          bigint auto_increment
        primary key,
    created_at  datetime(6) null,
    deleted_at  datetime(6) null,
    updated_at  datetime(6) null,
    notice_id   bigint      null,
    semester_id bigint      null,
    constraint FK8mkmayy20oparl4o8j1egwjmm
        foreign key (notice_id) references notice (notice_id),
    constraint FKkrefysaigit5q3i5jk6vmicch
        foreign key (semester_id) references semester (id)
);

create table participation_event
(
    created_at              datetime(6) null,
    deleted_at              datetime(6) null,
    event_form_id           bigint      not null,
    event_id                bigint      not null,
    id                      bigint auto_increment
        primary key,
    participation_mamber_id bigint      not null,
    updated_at              datetime(6) null,
    constraint FK5mirf91nccp9743qyek9g683k
        foreign key (event_form_id) references event_form (id),
    constraint FK9aamod8ekdh4a2n0rvwgxkvy0
        foreign key (participation_mamber_id) references member (id),
    constraint FKxankrpijjnerbyvs58s3gf4r
        foreign key (event_id) references event (id)
);

create table event_form_answer
(
    created_at             datetime(6)  null,
    deleted_at             datetime(6)  null,
    id                     bigint auto_increment
        primary key,
    participation_event_id bigint       not null,
    question_id            bigint       not null,
    updated_at             datetime(6)  null,
    answer_text            varchar(255) null,
    file_path              varchar(255) null,
    constraint FKlkl2f6riuls1v72pd7hcldknv
        foreign key (question_id) references event_form_question (id),
    constraint FKtpi3m1do9r1fqamyki487681f
        foreign key (participation_event_id) references participation_event (id)
);

create table roadmap_semester
(
    created_at  datetime(6) null,
    deleted_at  datetime(6) null,
    id          bigint auto_increment
        primary key,
    roadmap_id  bigint      not null,
    semester_id bigint      not null,
    updated_at  datetime(6) null,
    constraint FK65hblkw9xnl68fl06ys8hubyx
        foreign key (semester_id) references semester (id),
    constraint FK757glri7cdllllxxb20w6h2e9
        foreign key (roadmap_id) references roadmap (id)
);

create table checklist
(
    created_at          datetime(6)                                                     null,
    deleted_at          datetime(6)                                                     null,
    id                  bigint auto_increment
        primary key,
    roadmap_semester_id bigint                                                          not null,
    updated_at          datetime(6)                                                     null,
    title               varchar(255)                                                    not null,
    checklist_category  enum ('ATTENDANCE', 'KEYWORD', 'MISSION', 'PRACTICE', 'THEORY') not null,
    checklist_type      enum ('MULTIPLE', 'SELECT')                                     not null,
    constraint FKgquhqi0ptylo0d2y6fisqgg1b
        foreign key (roadmap_semester_id) references roadmap_semester (id)
);

create table checklist_content
(
    checklist_id bigint       not null,
    created_at   datetime(6)  null,
    deleted_at   datetime(6)  null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime(6)  null,
    content      varchar(255) not null,
    constraint FKstds9fohabp67tg8c1bjovra6
        foreign key (checklist_id) references checklist (id)
);

create table semester_current
(
    created_at  datetime(6) null,
    deleted_at  datetime(6) null,
    id          bigint auto_increment
        primary key,
    semester_id bigint      not null,
    updated_at  datetime(6) null,
    constraint UKmb6gxlqwq3mjvg3b63plrl8u5
        unique (semester_id),
    constraint FKkx0b757jjyoijbidye7hrj867
        foreign key (semester_id) references semester (id)
);

create table semester_part
(
    created_at  datetime(6)                                                        null,
    deleted_at  datetime(6)                                                        null,
    id          bigint auto_increment
        primary key,
    member_id   bigint                                                             not null,
    semester_id bigint                                                             not null,
    updated_at  datetime(6)                                                        null,
    part        enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') null,
    constraint FK7uds37hy3hwu4udlv69ted78h
        foreign key (member_id) references member (id),
    constraint FKhng44va5obc9ujbt513oj0hxi
        foreign key (semester_id) references semester (id)
);

create table semester_position
(
    created_at          datetime(6)  null,
    deleted_at          datetime(6)  null,
    id                  bigint auto_increment
        primary key,
    member_id           bigint       not null,
    semester_id         bigint       not null,
    updated_at          datetime(6)  null,
    central_position    varchar(255) null,
    university_position varchar(255) null,
    constraint FKmms5jj6aghjybn9p4frx32ypb
        foreign key (semester_id) references semester (id),
    constraint FKrbxsg3fpugknaxuxsv10issvm
        foreign key (member_id) references member (id)
);

create table study
(
    current_week int                                not null,
    created_at   datetime(6)                        null,
    deleted_at   datetime(6)                        null,
    id           bigint auto_increment
        primary key,
    updated_at   datetime(6)                        null,
    name         varchar(255)                       not null,
    study_type   enum ('ADMIN', 'BRANCH', 'SCHOOL') not null
);

create table study_member
(
    created_at       datetime(6)                   null,
    deleted_at       datetime(6)                   null,
    id               bigint auto_increment
        primary key,
    semester_part_id bigint                        not null,
    study_id         bigint                        not null,
    updated_at       datetime(6)                   null,
    study_role       enum ('CHALLENGER', 'LEADER') null,
    constraint FKaay05lkm25phq12uxyb3nbt1m
        foreign key (semester_part_id) references semester_part (id),
    constraint FKxu4jds4ab0mfyrvdxsu60iut
        foreign key (study_id) references study (id)
);

create table checklist_member_answer
(
    check_status         bit         not null,
    checklist_content_id bigint      not null,
    created_at           datetime(6) null,
    deleted_at           datetime(6) null,
    id                   bigint auto_increment
        primary key,
    study_member_id      bigint      not null,
    updated_at           datetime(6) null,
    constraint FK38u3xhl4motd6wgyvf60v8f31
        foreign key (study_member_id) references study_member (id),
    constraint FKjimvxvgeep3f55t2cromc7l8j
        foreign key (checklist_content_id) references checklist_content (id)
);

create table study_attendance
(
    week            int                                    not null,
    created_at      datetime(6)                            null,
    deleted_at      datetime(6)                            null,
    id              bigint auto_increment
        primary key,
    study_member_id bigint                                 not null,
    updated_at      datetime(6)                            null,
    check_status    enum ('NO', 'PARTIAL', 'UNSET', 'YES') null,
    constraint FKj6syi3salngpyo2ix142k0xnx
        foreign key (study_member_id) references study_member (id)
);

create table suggestion
(
    anonymity_status  bit                                      not null,
    completed_status  bit                                      not null,
    created_at        datetime(6)                              null,
    deleted_at        datetime(6)                              null,
    id                bigint auto_increment
        primary key,
    member_id         bigint                                   null,
    updated_at        datetime(6)                              null,
    content           varchar(255)                             null,
    title             varchar(255)                             null,
    suggestion_target enum ('BRANCH', 'CENTRAL', 'UNIVERSITY') null,
    constraint FK4yynic6ik0hwd5s8phq37c54e
        foreign key (member_id) references member (id)
);

create table suggestion_comment
(
    bundle_id     bigint       not null,
    created_at    datetime(6)  null,
    deleted_at    datetime(6)  null,
    depth         bigint       not null,
    id            bigint auto_increment
        primary key,
    member_id     bigint       null,
    parent_id     bigint       null,
    suggestion_id bigint       null,
    updated_at    datetime(6)  null,
    comment       varchar(200) not null,
    constraint FKesnw8ml5bd3ufgkuogejoilqo
        foreign key (suggestion_id) references suggestion (id),
    constraint FKi080g3ygseng8n04kafpkswy
        foreign key (member_id) references member (id),
    constraint FKs3l4s1tmb6ibmwqn9bqcxkpc8
        foreign key (parent_id) references suggestion_comment (id)
);

create table task
(
    created_at datetime(6)                                                        null,
    deleted_at datetime(6)                                                        null,
    id         bigint auto_increment
        primary key,
    updated_at datetime(6)                                                        null,
    content    varchar(255)                                                       not null,
    part       enum ('ANDROID', 'DESIGN', 'IOS', 'NODE', 'PLAN', 'SPRING', 'WEB') null,
    phase      enum ('FIRST', 'SECOND', 'THIRD')                                  null
);

create table project_task
(
    finish_status bit         not null,
    created_at    datetime(6) null,
    deleted_at    datetime(6) null,
    id            bigint auto_increment
        primary key,
    project_id    bigint      not null,
    task_id       bigint      not null,
    updated_at    datetime(6) null,
    constraint FKoki9jr57ykahgi2wve418pxvi
        foreign key (project_id) references project (id),
    constraint FKp29w9fu0wsxl6y6y8561c2e3c
        foreign key (task_id) references task (id)
);

create table university
(
    is_active     bit          not null,
    created_at    datetime(6)  null,
    deleted_at    datetime(6)  null,
    university_id bigint auto_increment
        primary key,
    updated_at    datetime(6)  null,
    name          varchar(255) not null
);

create table branch_university
(
    is_active            bit         not null,
    branch_id            bigint      not null,
    branch_university_id bigint auto_increment
        primary key,
    created_at           datetime(6) null,
    deleted_at           datetime(6) null,
    university_id        bigint      not null,
    updated_at           datetime(6) null,
    constraint FKf2resaj8wtecibksngacq4aw
        foreign key (university_id) references university (university_id),
    constraint FKns639gulv571biux2qernc0vt
        foreign key (branch_id) references branch (branch_id)
);

create table project_university
(
    created_at    datetime(6) null,
    deleted_at    datetime(6) null,
    id            bigint auto_increment
        primary key,
    project_id    bigint      not null,
    university_id bigint      not null,
    updated_at    datetime(6) null,
    constraint FKa9ji0vjbe9dfd032jyf72jm3i
        foreign key (project_id) references project (id),
    constraint FKajqpprxhte2g2bqlmwnevvcq7
        foreign key (university_id) references university (university_id)
);

create table study_university
(
    created_at    datetime(6) null,
    deleted_at    datetime(6) null,
    id            bigint auto_increment
        primary key,
    study_id      bigint      not null,
    university_id bigint      not null,
    updated_at    datetime(6) null,
    constraint FK1fxkqexio8obv7dyqxyi6oeix
        foreign key (study_id) references study (id),
    constraint FK91hb4mnibmrf35fv286a2fylr
        foreign key (university_id) references university (university_id)
);

