alter table parent_account.parent
    add column credits INTEGER not null default 0;

update parent_account.parent
set credits = FLOOR(random()*(10-5+1)+5);