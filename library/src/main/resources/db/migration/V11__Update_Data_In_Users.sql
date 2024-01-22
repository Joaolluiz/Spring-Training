UPDATE users
SET password =
        CASE
            WHEN id = 1 THEN '251e2986b69501829276456cda3b839ee72e8c06dc9edcc464fbacb18a0535bdd6908a8323eed32a'
            WHEN id = 2 THEN 'af30aa6713cc4504bc1967c0a7d8ac2fb3f392ca0cee305009ca43d90fafecc46e58d7ef0c4c0164'
            ELSE password
            END
WHERE id IN (1, 2);
