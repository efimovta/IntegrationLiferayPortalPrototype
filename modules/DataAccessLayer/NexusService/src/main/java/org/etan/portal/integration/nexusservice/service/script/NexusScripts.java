package org.etan.portal.integration.nexusservice.service.script;

import com.google.gson.Gson;
import org.etan.portal.integration.nexusservice.service.dto.NexusScriptDto;

/**
 * @author Naryzhny Alex
 */
public class NexusScripts {
    private static final String SCRIPT_TYPE = "groovy";

    public NexusScriptDto getCreateMavenRepositoryScript(String repositoryName) {
        String script = "" +
                "import org.sonatype.nexus.blobstore.api.BlobStoreManager;\n" +
                "import org.sonatype.nexus.repository.storage.WritePolicy;\n" +
                "import org.sonatype.nexus.repository.maven.VersionPolicy;\n" +
                "import org.sonatype.nexus.repository.maven.LayoutPolicy;\n" +
                "\n" +
                "def argsMap = args.split('&').inject([:]) { map, token ->\n" +
                "    token.split('=').with { map[it[0]] = it[1] }\n" +
                "    map\n" +
                "}\n" +
                "\n" +
                "def repositoryName = argsMap.getAt(\"repositoryName\")\n" +
                "\n" +
                "repository.createMavenHosted(repositoryName, BlobStoreManager.DEFAULT_BLOBSTORE_NAME, true,\n" +
                "        VersionPolicy.MIXED, WritePolicy.ALLOW, LayoutPolicy.STRICT)";
        String scriptName = NexusScriptAction.CREATE_MAVEN_HOSTED.getAction();
        NexusScriptDto nexusScriptDto = new NexusScriptDto.Builder().setScriptName(scriptName).
                setScriptType(SCRIPT_TYPE).setScriptContent(script).build();
        return nexusScriptDto;
    }

    public NexusScriptDto getAssignUserToRepositoryScript(String userId, String repositoryId) {
        String script = "" +
                "import org.sonatype.nexus.security.user.UserManager;\n" +
                "import org.sonatype.nexus.security.role.NoSuchRoleException;\n" +
                "\n" +
                "def argsMap = args.split('&').inject([:]) { map, token ->\n" +
                "    token.split('=').with { map[it[0]] = it[1] }\n" +
                "    map\n" +
                "};\n" +
                "\n" +
                "def roleName = argsMap.getAt(\"roleName\").toString();\n" +
                "def privilegeName = argsMap.getAt(\"privilegeName\").toString();\n" +
                "def userId = argsMap.getAt(\"userId\").toString();\n" +
                "def user = security.securitySystem.getUser(userId);\n" +
                "\n" +
                "def authManager = security.getSecuritySystem().getAuthorizationManager(UserManager.DEFAULT_SOURCE);\n" +
                "def role = null;\n" +
                "try {\n" +
                "    role = authManager.getRole(roleName);\n" +
                "} catch (NoSuchRoleException e) {\n" +
                "    print \"There is no such role, it will be created!\";\n" +
                "}\n" +
                "List<String> privileges = new ArrayList<>();\n" +
                "privileges.add(privilegeName);\n" +
                "\n" +
                "if (role == null) {\n" +
                "    role = security.addRole(roleName, roleName, \"\", privileges, new ArrayList<String>());\n" +
                "}\n" +
                "List<String> roles = new ArrayList<>();\n" +
                "roles.add(role.getRoleId());\n" +
                "user.getRoles().each {\n" +
                "    roles.add(it.getRoleId());\n" +
                "};\n" +
                "user = security.setUserRoles(userId, roles);\n" +
                "return user;";

        String scriptName = NexusScriptAction.ASSIGN_USER.getAction();
        NexusScriptDto nexusScriptDto = new NexusScriptDto.Builder().setScriptName(scriptName).
                setScriptType(SCRIPT_TYPE).setScriptContent(script).build();
        return nexusScriptDto;
    }

    public NexusScriptDto getUnassignUserToRepositoryScript(String userId, String repositoryId) {
        return null;
    }

    public NexusScriptDto getLastArtifactsScript(String repositoryId, int artifactsCount) {
        return null;
    }


    public static void main(String[] args) {
        Gson gson = new Gson();
        NexusScriptDto assignUserToRepositoryScript = new NexusScripts().getAssignUserToRepositoryScript(null, null);
        String s = gson.toJson(assignUserToRepositoryScript);
        System.out.println();

    }

}
