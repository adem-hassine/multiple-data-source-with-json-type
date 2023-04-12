package com.multidatasource.demo.postgres;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.*;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import lombok.Getter;
import org.hibernate.annotations.Type;

public class JsonPath implements Path<Object> {

    protected final PathMetadata metadata;
    protected final String property;
    protected final Path<?> parent;
    protected final ObjectMapper mapper;
    @Getter protected final boolean jsonb;

    public JsonPath(Path<?> parent) {
        this(parent, ".");
    }

    protected JsonPath(Path<?> parent, String property) {
        this(parent, property, isJsonb(parent));
    }

    protected JsonPath(Path<?> parent, String property, boolean jsonb) {
        this.metadata = PathMetadataFactory.forProperty(parent, property);
        this.property = property;
        this.parent = parent;
        this.jsonb = jsonb;
        this.mapper = new ObjectMapper();
    }

    public static boolean isJsonb(Path<?> path) {
        if (path instanceof JsonPath) {
            return ((JsonPath) path).isJsonb();
        }
        Type type = path.getAnnotatedElement().getAnnotation(Type.class);
        if (type != null) {
            return type.type().contains("jsonb");
        }
        return false;
    }


    @Override
    public PathMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Path<?> getRoot() {
        return metadata.getRootPath();
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return parent.getAnnotatedElement();
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        throw new AssertionError("This should not happen");
    }

    protected List<Object> properties() {
        List<Object> p;

        if (parent instanceof JsonPath) {
            p = ((JsonPath) parent).properties();
        } else {
            p = new ArrayList<>();
            p.add(parent);
        }
        if (!property.equals(".")) {
            p.add(property);
        }
        return p;
    }

    @Override
    public Class<? extends Object> getType() {
        return Object.class;
    }

    public StringExpression asText() {
        List<Object> args = properties();
        StringBuilder sb = new StringBuilder();
        sb.append("json_extract_path_text").append('(');
        sb.append("to_json").append('(');
        generateArgs(sb,1);
        sb.append(')').append(',');
        generateArgs(sb, 1, 1);
        sb.append(')');
        return Expressions.stringTemplate(sb.toString(), args);
    }







    public JsonPath get(String property) {
        return new JsonPath(this, property);
    }

    public JsonPath get(String... properties) {
        JsonPath p = this;
        for (String s : properties) {
            p = p.get(s);
        }
        return p;
    }

    protected CharSequence generateArgs(StringBuilder sb, int n) {
        return generateArgs(sb, 0, n - 1);
    }

    protected CharSequence generateArgs(StringBuilder sb, int start, int end) {
        for (int i = start; i <= end; i++) {
            sb.append('{').append(i).append('}');
            if (i != end) {
                sb.append(',');
            }
        }
        return sb;
    }

}
