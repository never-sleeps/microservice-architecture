CREATE UNIQUE INDEX billing_account_email_idx
    ON "public"."billing_account" (email ASC);

CREATE UNIQUE INDEX billing_account_user_id_idx
    ON "public"."billing_account" (user_id ASC);