package me.ikevoodoo.fabrigot.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;

import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public final class FabrigotCommandRegister {

    private final CommandDispatcher<ServerCommandSource> dispatcher;
    private final FabrigotCommandTranslator translator;

    public FabrigotCommandRegister(CommandDispatcher<ServerCommandSource> dispatcher, FabrigotCommandTranslator translator) {
        this.dispatcher = dispatcher;
        this.translator = translator;
    }

    public void registerSpigot(final String label, final Command command, final TabCompleter completer) {
        this.dispatcher.register(
            registerExecutor(literal(command.getName()), command, completer)
        );

        this.dispatcher.register(
            registerExecutor(literal(label + ":" + command.getName()), command, completer)
        );
    }

    private LiteralArgumentBuilder<ServerCommandSource> registerExecutor(LiteralArgumentBuilder<ServerCommandSource> argumentBuilder,
                                                                         final Command command,
                                                                         final TabCompleter completer) {
        return argumentBuilder
                .then(
                    argument("args", greedyString())
                        .suggests((context, builder) -> {
                            var args = builder.getInput().split(" ");
                            if (completer == null) return builder.buildFuture();

                            var list = completer.onTabComplete(
                                    this.translator.toSpigot(context.getSource()),
                                    command,
                                    command.getName(),
                                    args
                            );

                            if (list == null) {
                                return builder.buildFuture();
                            }

                            var size = builder.getInput().trim().length() + args.length - 1;

                            builder = builder.createOffset(size);
                            list.forEach(builder::suggest);
                            return builder.buildFuture();
                        })
                        .executes(context -> {
                            String args = context.getArgument("args", String.class);

                            return this.execute(
                                    command,
                                    context.getSource(),
                                    args.split(" ")
                            );
                        })
                )
                .executes(context -> this.execute(
                        command,
                        context.getSource()
                ));
    }

    private int execute(final Command command, CommandSource source, String... args) {
        System.out.println("Executing with args: " + Arrays.toString(args));
        try {
            boolean success = command.execute(
                    this.translator.toSpigot(source),
                    command.getLabel(),
                    args
            );

            System.out.println("Success: " + success);

            return success ? 1 : 0;
        } catch(Throwable throwable) {
            throwable.printStackTrace();

            return 0;
        }
    }
}
