<script lang="ts">
  import { Card, Content, Header, Title } from "$lib/components/ui/card";

  import * as Form from "$lib/components/ui/form";
  import * as Select from "$lib/components/ui/select";
  import { Input } from "$lib/components/ui/input";

  import { superForm, setError, defaults } from "sveltekit-superforms";
  import { registerSchema, roles } from "./schema";
  import { valibot } from "sveltekit-superforms/adapters";
  import { goto } from "$app/navigation";

  const form = superForm(defaults(valibot(registerSchema)), {
    SPA: true,
    validators: valibot(registerSchema),
    onUpdate: async ({ form }) => {
      if (!form.valid) {
        return;
      }

      let response;

      try {
        response = await fetch("https://ordersync.online/api/register", {
          method: "POST",
          credentials: "include",
          mode: "cors",
          body: JSON.stringify(form.data)
        });
      } catch (e) {
        return setError(form, "Something went really wrong :-(");
      }

      if (response.status === 200) {
        goto("/");
      } else {
        setError(form, response.statusText);
      }
    }
  });

  const { form: formData, errors, enhance } = form;
</script>

<div class="flex h-full w-full flex-grow items-center justify-center">
  <Card class="w-96">
    <Header>
      <Title>Register</Title>
    </Header>
    <Content>
      <form method="POST" use:enhance class="flex flex-col">
        <Form.Field {form} name="email">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Email</Form.Label>
              <Input {...props} type="email" bind:value={$formData.email} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        <Form.Field {form} name="password">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Password</Form.Label>
              <Input {...props} type="password" bind:value={$formData.password} />
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        <Form.Field {form} name="role">
          <Form.Control>
            {#snippet children({ props })}
              <Form.Label>Role</Form.Label>
              <Select.Root type="single" name={props.name} bind:value={$formData.role}>
                <Select.Trigger class="flex w-full"
                  >{$formData.role ? $formData.role.toLowerCase() : "Select a role"}</Select.Trigger
                >
                <Select.Content>
                  {#each Object.values(roles) as role}
                    <Select.Item value={role} label={role.toLowerCase()}
                      >{role.toLowerCase()}</Select.Item
                    >
                  {/each}
                </Select.Content>
              </Select.Root>
            {/snippet}
          </Form.Control>
          <Form.FieldErrors />
        </Form.Field>

        {#if $errors._errors}
          <div class="mt-3 w-full text-center text-red-500">
            {$errors._errors!.join(",")}
          </div>
        {/if}

        <Form.Button class="mt-3">Submit</Form.Button>
      </form>
    </Content>
  </Card>
</div>
