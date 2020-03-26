INSERT INTO oauth_client_details 
(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES
("client-id", "$2a$10$GEqNDMckLYgJxZfRRmP/n.aZUF0u3a.gHPTgCOT.bWTqLRj/PR2qy", "read,write", "password,authorization_code,refresh_token", null, null, 36000, 36000, null, true);